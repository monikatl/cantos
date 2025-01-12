package com.example.singandsongs.ui.calendar.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.day.DayRepository
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.FullDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
  private val dayRepository: DayRepository
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

  fun deletePlaying(day: Day?) {

  }
}
