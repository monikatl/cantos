package com.example.singandsongs.model.playing

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Calendar(days: List<Day>) {


  companion object {
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateMonth() {
      val today = LocalDate.now()
      val currentMonth = today.month
      val currentDayName = today.dayOfWeek
    }

    fun generate(): List<Day> {
      val list = emptyList<Day>().toMutableList()
      repeat(48) {
        list.add(Day())
      }
      return list
    }
  }


}
