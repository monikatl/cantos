package com.example.singandsongs.ui.calendar.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singandsongs.data.day.DayRepository
import com.example.singandsongs.model.playing.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
  private val dayRepository: DayRepository
) : ViewModel() {

  var days: LiveData<List<Day>> = MutableLiveData(dayRepository.getAllDays())

  fun deletePlaying(day: Day) {

  }
}
