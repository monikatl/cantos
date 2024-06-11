package com.example.benedictus.src.main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Canto implements Piece {

  private Delimiter delimiterBuilder;
  private String fileName;
  private int sheetCounter;
  private int number;
  private String cantoName;

  private Kind kind;

  // 0 - tytułowa
  // 1 - 127 - tekst
  private List<Sheet> sheets;

  private String formattedText;


  public Canto() {

  }

  public Canto(String fileName, String cantoName, int number, int sheetCounter, String formattedText) {
    this.fileName = fileName;
    this.cantoName = cantoName;
    this.number = number;
    this.sheetCounter = sheetCounter;
    this.sheets = new ArrayList<>(sheetCounter);
    this.formattedText = formattedText;
  }

  public Canto(String formattedText) {
    this.formattedText = formattedText;
  }

  public Canto(String cantoName, List<Sheet> sheets, String fileName) {
    this.cantoName = cantoName;
    this.sheets = sheets;
    this.fileName = fileName + ".txt";
  }

  public void setDelimiterBuilder(Delimiter delimiterBuilder) {
    this.delimiterBuilder = delimiterBuilder;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setSheetCounter(int sheetCounter) {
    this.sheetCounter = sheetCounter;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public void setCantoName(String cantoName) {
    this.cantoName = cantoName;
  }

  public void setSheets(List<Sheet> sheets) {
    this.sheets = sheets;
  }

  public Delimiter getDelimiterBuilder() {
    return delimiterBuilder;
  }

  public String getFileName() {
    return fileName;
  }

  public int getSheetCounter() {
    return sheetCounter;
  }

  public int getNumber() {
    return number;
  }

  public String getCantoName() {
    return cantoName;
  }

  public List<Sheet> getSheets() {
    return sheets;
  }

  public String getNumberAndTitleSpaces() {
    return String.format("%-9d %s", number, cantoName);
  }

  public String getNumberAndTitle() {
    return number + " " + cantoName;
  }

  public String getNumberTitleAndSheetCounter() {
    return number + " " + cantoName + " " + getSheetCounterWithPrefix();
  }

  public String getSheetCounterWithPrefix() {
    return "$" + sheetCounter;
  }
  public String getNumberAndSheets() {
    return number + " " + sheets.stream().map(Sheet::getText).collect(Collectors.joining());
  }

  public String getNumberTitleCounterAndSheets() {
    return number + "&" + cantoName + "&" + fileName + "&" + sheetCounter + "&" + sheets.stream().map(Sheet::getText).collect(Collectors.joining("&")) + "^";
  }


  public String getFormattedText() {
    return formattedText;
  }

  public void generateTXT() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      generateFormattedText();
      writer.write(formattedText);
      System.out.println("File created and content written successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String generateFormattedText() {
    StringBuilder text = new StringBuilder();
    sheets.forEach(sheet -> text.append(sheet.toString()));
    formattedText = text.toString();
    return formattedText;
  }

  public void setFormattedText(String formattedText) {
    this.formattedText = formattedText;
  }
}
