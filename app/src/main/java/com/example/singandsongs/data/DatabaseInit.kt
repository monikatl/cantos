package com.example.singandsongs.data

import android.content.Context
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.NumberFormatException
import java.util.stream.Collectors
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
      val text = readFromAssets(context, fileName)
      text.forEach { stringCanto ->
        val canto = convertToCanto(stringCanto)
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
          }
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return lines
  }

//  private fun convertToCanto(textCanto: String): Canto {
//    val text = textCanto.split("&")
//    println(text)
//    val number =  text[0].toInt()
//    val title = text[1]
//    val fileName = text[2]
//    val sheetCounter = text[3].toInt()
//    val sheets = text.subList(4, text.lastIndex).stream().collect(Collectors.joining())
//    return Canto(number, title, Kind.ACCIDENTAL, sheetCounter, "" ,fileName )
//  }

  private fun convertToCanto(textCanto: String): Canto {
    val index = textCanto.indexOfFirst { it == ' ' }
    val number = if(index != -1) textCanto.substring(0, index).toInt() else 3
    val title = textCanto.substring(index+1).split('$')[0]
    val sheetsCounter = textCanto.substring(index+1).split('$')[1].toInt()
    return Canto(number, title, Kind.ACCIDENTAL, sheetsCounter)
  }
}


