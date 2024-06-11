package com.example.benedictus.src.main.java;

import java.util.*;

public class Sheet {
  public static final int ROW_COUNT = 8;
  public static final int COLUMN_COUNT = 20;
  private String delimiter = " -------";
  private int number;
  private String text;
  private int rows;

  public Sheet() {};

  public Sheet(int number, String text) {
    this.number = number;
    this.text = generateFormattedSheet(text);
    this.delimiter = generateDelimiter(number);
  }

  public static Sheet loadSheet(int number, String text) {
    Sheet sheet = new Sheet();
    sheet.setNumber(number);
    sheet.setText(text);
    return sheet;
  }
  private String generateFormattedSheet(String text){
    String [] tabText = text.split(" ");
    Collections.reverse(Arrays.asList(tabText));
    Stack<String> words = new Stack<>();
    List<String> currentLineWords = new ArrayList<>();
    Collections.addAll(words, tabText);
    StringBuilder sheet = new StringBuilder();

    for(int i = 0; i < Sheet.ROW_COUNT; i++) {
      int lineSum = 0;
      currentLineWords.clear();
      while (true) {
        if(words.isEmpty())
          break;
        int currentWordLength = words.peek().length();
        lineSum += currentWordLength;
        System.out.println(lineSum);
        if(lineSum <= 20) {
          currentLineWords.add(words.pop());
          lineSum++;
        } else break;
      }
      currentLineWords.forEach(word -> sheet.append(word).append(" "));
      sheet.append('\n');
      if (words.isEmpty()){
        break;
      }
    }
    return sheet.toString();
  }

  private String generateDelimiter(int number) {
    StringBuilder sb = new StringBuilder();
    return sb.append(Delimiter.DOTTED)
      .append("\n")
      .append(Delimiter.START)
      .append(convert(number))
      .append(Delimiter.END)
      .append("\n")
      .append(Delimiter.DOTTED)
      .toString();
  }

  private String convert(int number) {
    String stringNumber = String.valueOf(number);
    if (stringNumber.length()==1)
      stringNumber = "00" + stringNumber;
    if(stringNumber.length()==2)
      stringNumber = "0" + stringNumber;
    return stringNumber;
  }

  @Override
  public String toString() {
    return delimiter + "\n" + text;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getText() {
    return "#" + text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
