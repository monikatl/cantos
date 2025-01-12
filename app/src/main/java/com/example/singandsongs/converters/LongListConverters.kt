package com.example.singandsongs.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LongListConverters {
  @TypeConverter
  fun fromList(value: List<Long>): String {
    return Gson().toJson(value)
  }

  @TypeConverter
  fun toList(value: String): List<Long> {
    val listType = object : TypeToken<List<Long>>() {}.type
    return Gson().fromJson(value, listType)
  }
}
