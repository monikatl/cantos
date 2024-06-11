package com.example.benedictus.src.main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CantoFormatter {
  private String name;
  private List<Verse> verses;
  private List<Sheet> sheets = new ArrayList<>();

  public Canto createCanto(String name, String text, int fileName) {
    createTextTable(text, name, fileName);
    Canto canto = new Canto(name, this.sheets, String.valueOf(fileName));
    return canto;
  }

  private void createTextTable(String text, String name, int fileName) {
    this.verses = createVerses(text);
    for (Verse s : this.verses) {
      if(s.getNumber() == 0) {
        Sheet titleSheet = createTitlePage(name, fileName);
        this.sheets.add(titleSheet);
        continue;
      }
      List<Sheet> temp = s.generateSheets();
      this.sheets.addAll(temp);
    }
  }

  private List<Verse> createVerses(String text) {
    List<String> textVerses = Arrays.stream(text.split("\\d+. ")).toList();
    return textVerses.stream().map(verse -> new Verse(textVerses.indexOf(verse), verse.toUpperCase())).toList();
  }

  private Sheet createTitlePage(String name, int fileName) {
    return new Sheet(0, name);
  }
}
