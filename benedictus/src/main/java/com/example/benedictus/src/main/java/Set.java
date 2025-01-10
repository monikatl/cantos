package com.example.benedictus.src.main.java;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Collectors;

public class Set implements Piece{

  public static int setCounter = 0;
  public static final String T_START = "<T>";
  public static final String Z_START = "<Z>";
  public static final String T_END = "</T>";
  public static final String Z_END = "</Z>";
  private String fileName;
  private String title;

  private int number;
  private Map<Integer, Integer> cantos;

  private String customPath = "Benedictus/SETS";

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

  public void saveFileToExternalStorage(Context context) {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      File externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
      if (!externalStorageDir.exists()) {
        externalStorageDir.mkdirs();
      }
      File file = new File(externalStorageDir, fileName);
      try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
        fileOutputStream.write(formattedString().getBytes());
        Log.d("TAG", "File created and content written successfully at " + file.getAbsolutePath());
      } catch (IOException e) {
        Log.e("TAG", "Error while writing to file: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  public void saveFileToExternalPath(Context context) {
    if (isExternalStorageWritable()) {
      File externalStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), customPath);
      if (!externalStorageDir.exists()) {
        if (!externalStorageDir.mkdirs()) {
          Log.e(TAG, "Directory creation failed.");
          return;
        }
      }
      File file = new File(externalStorageDir, fileName);
      try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
        fileOutputStream.write(formattedString().getBytes());
        Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "File created and content written successfully at " + file.getAbsolutePath());
      } catch (IOException e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error while writing to file: " + e.getMessage());
        e.printStackTrace();
      }
    } else {
      Log.e(TAG, "SD card is not writable");
    }
  }

  private boolean isExternalStorageWritable() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
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

  private static String resolveNumber(int number) {
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
      Z_END;

    return sb;
  }

  public static String getNextSetFileName() {
    setCounter++;
    return resolveNumber(setCounter);
  }
}
