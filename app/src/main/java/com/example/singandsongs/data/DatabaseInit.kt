package com.example.singandsongs.data

import android.content.Context
import com.example.singandsongs.model.Canto
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class DatabaseInit @Inject constructor(private val cantoDao: CantoDao) {

    val cantos: MutableList<Canto> = mutableListOf()
    fun initialize(context: Context) {
      readFromTXTFile(context)
      for (canto in cantos) {
        cantoDao.insertCanto(canto)
      }
    }

    private fun readFromTXTFile(context: Context) {
      val fileName = "/assets/cantos.txt"
      val lines = readFromAssets(context, fileName)
      lines.forEach { line ->
        val canto = convertToCanto(line)
        cantos.add(canto)
      }
    }

  private fun readFromAssets(context: Context, fileName: String): List<String> {
    val lines = mutableListOf<String>()
    try {
      context.assets.open("cantos.txt").use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).useLines { sequence ->
          sequence.forEach { line ->
            lines.add(line)
            println(line)
          }
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return lines
  }

  private fun convertToCanto(textCanto: String): Canto {
    val index = textCanto.indexOfFirst { it == ' ' }
    val number = if(index != -1) textCanto.substring(0, index).toInt() else 3
    val title = textCanto.substring(index+1)
    return Canto(number, title)
  }
}


