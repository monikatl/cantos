package com.example.benedictus.src.main.java;

import java.util.ArrayList;
import java.util.List;

public class Verse {
  private int number;
  private String textNumber;
  private String text;
  private int sheetCounter;

  public Verse(int number, String text) {
    this.number = number;
    this.text = text.replaceAll("\\*", "");
    this.textNumber = number + ". ";
  }

  public int getNumber() {
    return number;
  }

  public String getTextNumber() {
    return textNumber;
  }

  public String getText() {
    return text;
  }

  private boolean checkIfOneSheetVerse() {
    if(text.length()<=160)
      return true;
    else return false;
  }

  public List<Sheet> generateSheets() {
    List<Sheet> sheets = new ArrayList<>();
    if(!checkIfOneSheetVerse()) {

    } else {
      Sheet sheet = new Sheet(number, text);
      sheets.add(sheet);
    }
    return sheets;
  }
}
