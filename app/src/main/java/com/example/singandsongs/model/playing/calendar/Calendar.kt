package com.example.singandsongs.model.playing.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.singandsongs.model.playing.Day
import java.time.LocalDate
import java.time.YearMonth

class Calendar(days: List<Day>) {


  companion object {

    var creationCounter = 1

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateMonth(year: Int): List<Day?> {
      val today = LocalDate.now().minusDays(3)
      val currentMonth = today.month
      val currentDayCount = today.dayOfMonth
      val firstDayOfMonth = today.minusDays(currentDayCount.toLong() - 1L)

      val numberOfFirstDay = firstDayOfMonth.dayOfWeek.value

      val days = createNullList<Day?>(numberOfFirstDay - 1)

      val daysInMonth = checkMonthLength(year, currentMonth)

      repeat(daysInMonth) {
        days.add(Day.create(creationCounter, currentMonth.name, year))
        creationCounter++
      }
      creationCounter = 1

      println(days)

      return days
    }

    fun generate(): List<Day?> {
      val list = emptyList<Day>().toMutableList()
      repeat(42) {
        list.add(Day())
      }
      return list
    }

    fun generateYear() {

    }
  }


}
