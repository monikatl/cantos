package com.example.benedictus.src.main.java;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Collectors;

public class Set implements Piece{
  public static final String T_START = "<T>";
  public static final String Z_START = "<Z>";
  public static final String T_END = "</T>";
  public static final String Z_END = "</Z>";
  private String fileName;
  private String title;

  private int number;
  private Map<Integer, Integer> cantos;

  public Set(String fileName, String title, Map<Integer, Integer> cantos) {
    this.fileName = fileName;
    this.title = title;
    this.cantos = cantos;
    this.number = Integer.parseInt(fileName.split("\\.")[0]);
  }

  /*
  * <T>Tytuł</T>
  * <Z>
  *
  *
  * </Z>
  *
  *
  *
  * */

  private static final String TAG = "FileHelper";

  public void exportToTXT(Context context) {
    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
      context.openFileOutput(fileName, Context.MODE_PRIVATE)))) {
      writer.write(formattedString());
      Log.d(TAG, "File created and content written successfully.");
    } catch (IOException e) {
      Log.e(TAG, "Error while writing to file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public String getTitle() {
    return title;
  }

  public String getNumberWithTitle() {
    return number + " " + title;
  }

  public String formattedCantos() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<Integer, Integer> entry : cantos.entrySet()) {
      sb.append(resolveNumber(entry.getKey()))
        .append(";")
        .append(resolveSheetCounter(entry.getValue()))
        .append(";")
        .append("\n");
    }
    return sb.toString();
  }

  private String resolveNumber(int number) {
    String textNumber = String.valueOf(number);
    switch (textNumber.length()) {
      case 1 -> textNumber = "0000" + textNumber;
      case 2 -> textNumber = "000" + textNumber;
      case 3 -> textNumber = "00" + textNumber;
      case 4 -> textNumber = "0" + textNumber;
      default -> {
      }
    }
    return textNumber;
  }

  private String resolveSheetCounter(int number) {
    String textNumber = String.valueOf(number);
    switch (textNumber.length()) {
      case 1 -> textNumber = "00" + textNumber;
      case 2 -> textNumber = "0" + textNumber;
      default -> {
      }
    }
    return textNumber;
  }
  public String formattedString() {
    String sb = T_START +
      "\n" +
      title +
      "\n" +
      T_END +
      "\n" +
      Z_START +
      "\n" +
      formattedCantos() +
      "\n" +
      Z_END;

    return sb;
  }
}
