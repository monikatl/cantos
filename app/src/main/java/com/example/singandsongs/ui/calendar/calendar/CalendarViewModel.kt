package com.example.singandsongs.ui.calendar.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.day.DayRepository
import com.example.singandsongs.data.playing.PlayingRepository
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
  private val dayRepository: DayRepository,
  private val playingRepository: PlayingRepository
) : ViewModel() {

  private val _selectedDay = MutableLiveData<FullDay?>(null)
  val selectedDay: LiveData<FullDay?> = _selectedDay

  fun selectDay(day: FullDay) {
    viewModelScope.launch {
      _selectedDay.value = day
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  var days: LiveData<List<Day?>> = dayRepository.getAllDays().asLiveData()

  @RequiresApi(Build.VERSION_CODES.O)
  val fullDays: LiveData<List<FullDay>> = MediatorLiveData<List<FullDay>>().apply {
    addSource(days) { dayList ->
      value = dayList?.mapNotNull { day ->
        day?.let {
          convertDayToFullDay(it)
        }
      }
    }
  }

  private fun convertDayToFullDay(day: Day) =
    FullDay.convertToFullDay(day, resolvePlayingListForFullDay(day.playings))

  private fun resolvePlayingListForFullDay(list: List<Long>) =
    list.map {
      runBlocking{
        playingRepository.getPlayingById(it)
      }
    }

  @RequiresApi(Build.VERSION_CODES.O)
  suspend fun editDay(date: LocalDate, playingId: Long) {
    val day = getDayByDate(date)
    day?.let {
      println("MAMY DZIEŃ")
      it.addPlaying(playingId)
      updateDay(it)
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private suspend fun getDayByDate(date: LocalDate) =
    dayRepository.getDayByDate(date)


  private fun updateDay(day: Day) {
    viewModelScope.launch {
      println(day)
      dayRepository.updateDay(day)
    }
  }

  fun deletePlaying(day: Day?) {

  }
}
