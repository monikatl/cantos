package com.example.singandsongs.data.day

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.calendar.Calendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DayRepository @Inject constructor(
  private val dayDao: DayDao
) {
  @RequiresApi(Build.VERSION_CODES.O)
  fun getAllDays(): Flow<List<Day?>> {
    val days = dayDao.getAllDays()
    return flow {
      val dayList = days.first()
      if (dayList.isEmpty()) {
        val newDays = Calendar.generateMonth(2025)
        newDays.forEach { day ->
          if (day != null) {
            insertDay(day)
          }
        }
        emit(newDays)
      } else emit(dayList)
    }
  }

  @WorkerThread
  suspend fun insertDay(day: Day) = withContext(Dispatchers.IO) {
    dayDao.insertDay(day)
  }

  @WorkerThread
  suspend fun deleteDay(day: Day) = withContext(Dispatchers.IO) {
    dayDao.deleteDay(day)
  }

}
