package com.example.benedictus.src.main.java;

import java.io.*;

public class CantosReader {
  public String getCanto(String filePath) {
    StringBuilder canto = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        canto.append(line).append(System.lineSeparator());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return canto.toString();
  }
}
