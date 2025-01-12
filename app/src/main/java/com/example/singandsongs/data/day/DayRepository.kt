package com.example.singandsongs.data.day

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.singandsongs.model.playing.calendar.Calendar
import javax.inject.Inject

class DayRepository @Inject constructor(
  private val dayDao: DayDao
) {
  @RequiresApi(Build.VERSION_CODES.O)
  fun getAllDays() = Calendar.generateMonth(2025)
}
