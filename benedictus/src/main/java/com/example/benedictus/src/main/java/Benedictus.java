package com.example.benedictus.src.main.java;

import android.os.Environment;

import java.io.File;
import java.util.*;

public class Benedictus {

  public static List<Piece> pieces = new ArrayList<>();
  public static final Scanner input = new Scanner(System.in);

  private static final String textsPath = "/TEXT";
  private static final String setsPath = "/SETS";

  public static List<Set> readSets() {
    readData(setsPath, new SetConverter());
    return filterSets(pieces);
  }

  public static List<Canto> readTexts() {
    readData(textsPath, new CantoConverter());
    return filterCantos(pieces);
  }

  private static List<Canto> filterCantos(List<Piece> pieces) {
    return pieces.stream()
      .filter(piece -> piece instanceof Canto)
      .map(piece -> (Canto) piece)
      .toList();
  }

  private static List<Set> filterSets(List<Piece> pieces) {
    return pieces.stream()
      .filter(piece -> piece instanceof java.util.Set)
      .map(piece -> (Set) piece)
      .toList();
  }

  private static void readData(String folderPath, Converter converter) {
    CantosReader cantosReader = new CantosReader();

    File sdcard = Environment.getExternalStorageDirectory();

    File folder = new File(sdcard.getAbsolutePath() + folderPath);

    if (folder.exists() && folder.isDirectory()) {
      File[] listOfFiles = folder.listFiles();

      for (File file : listOfFiles) {
        if (file.isFile()) {
          String filePath = file.getAbsolutePath();
          String content = cantosReader.getCanto(filePath);
          Piece piece = converter.convert(content, file.getName());
          pieces.add(piece);
          System.out.println(piece);
        }
      }
    } else {
      System.out.println("Podana ścieżka nie jest katalogiem lub nie istnieje.");
    }
  }
}
