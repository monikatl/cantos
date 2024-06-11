package com.example.benedictus.src.main.java;

import java.util.*;

public class SetConverter implements Converter {

  @Override
  public Piece convert(String text, String fileName) {
    String title = text.replaceFirst("<T>", "").split("</T")[0].trim();
    return new Set(fileName, title, resolveCantos(text));
  }

  private Map<Integer, Integer> resolveCantos(String text) {
    List<String> cantos = Arrays.stream(Arrays.stream(text.split("<Z>")).toList().get(1).trim().split("\n")).toList();
    Map<Integer, Integer> cantosWithSheets = new HashMap<>();
    for (String canto : cantos) {
      if(canto.equals("</Z>")){
        break;
      }
      cantosWithSheets.put(Integer.parseInt(canto.split(";")[0]), Integer.parseInt(canto.split(";")[1].replace(";", "")));
    }
    return cantosWithSheets;
  }
}
