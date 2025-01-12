package com.example.singandsongs.model.playing.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Month
import java.time.YearMonth


fun <T> createNullList(counter: Int): MutableList<T?> {
  val list = emptyList<T?>().toMutableList()
  repeat(counter) {
    list.add(null)
  }
  return list
}

@RequiresApi(Build.VERSION_CODES.O)
fun checkMonthLength(year: Int, month: Month): Int {
  val yearMonth = YearMonth.of(year, month)
  return yearMonth.lengthOfMonth()
}

