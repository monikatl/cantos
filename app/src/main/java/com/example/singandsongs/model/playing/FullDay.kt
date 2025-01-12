package com.example.singandsongs.model.playing

data class FullDay (
  val number: Int = 1,
  val monthNumber: Int = 1,
  val year: Int = 2025,
  val playings: List<Playing> = emptyList(),
  val day: Day
) {
  companion object {
    fun convertToFullDay(day: Day, playings: List<Playing>) =
      FullDay(day.number, day.monthNumber, day.year, playings, day)
  }
}
