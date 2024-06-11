package com.example.benedictus.src.main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CantoConverter implements Converter{

  String delimiter = "<------------------>\\s*<- Strona nr: \\d{3} ->\\s*<------------------>";
  private String title;

  @Override
  public Piece convert(String text, String fileName) {
    Canto canto = new Canto(text);
    canto.setFileName(fileName);

    List<Sheet> sheets = new ArrayList<>();

    // canto without
    //<------------------>
    //<- Strona nr: 000 ->
    //<------------------>

    //number + title
    System.out.println(text);
    String trimText = text.substring(64);
    //podział na strony
    String [] textSheets = trimText.split(delimiter);

    //strona tytułowa
    this.title = textSheets[0].trim();


    // pierwsza strona numer pieśni i tytuł
    int cantoNumber = resolveCantoNumber(fileName);
    canto.setNumber(cantoNumber);

    String cantoTitle = resolveCantoTitle();
    canto.setCantoName(cantoTitle);

    // strony

    for (int i = 1; i < textSheets.length; i++) {
      sheets.add(Sheet.loadSheet(i, textSheets[i]));
    }

    canto.setSheets(sheets);

    // liczba stron
    canto.setSheetCounter(sheets.size());

    return canto;
  }

  // Optional
  private int resolveCantoNumber(String fileName) {
    int number = 0;
    String regex = "\\d+";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(title);
    if (matcher.find()) {
      String textNumber = matcher.group();
      number = Integer.parseInt(textNumber);
      int endIndex = matcher.end();
      this.title = title.substring(endIndex);
    }
    if(number==0) {
      return Integer.parseInt(fileName.split("\\.")[0]);
    }
    return number;
  }

  private String resolveCantoTitle() {
    String title;
    String regex = "\\d";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(this.title);
    if (matcher.find()) {
      int startIndex = matcher.start();
      title = this.title.substring(0, startIndex);
      this.title = this.title.substring(startIndex);
    } else {
      title = this.title;
    }
    title = title
              .trim()
              .replaceAll("\\r?\\n", " ")
              .replaceAll("\\s+", " ");
    return title;
  }

  private int resolveSheetCounter() {
    int sheetNumber = 0;

    return sheetNumber;
  }
}
