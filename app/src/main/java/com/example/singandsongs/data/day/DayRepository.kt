package com.example.singandsongs.data.day

import com.example.singandsongs.model.playing.Calendar
import javax.inject.Inject

class DayRepository @Inject constructor(
  private val dayDao: DayDao
) {
  fun getAllDays() = Calendar.generate()
}
