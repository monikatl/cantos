package com.example.benedictus.src.main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TxtWriter {
   public static void exportToTXT(List<Canto> cantos, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      String formattedText = cantos.stream().filter(canto -> !canto.getCantoName().isEmpty()).map(Canto::getNumberTitleCounterAndSheets).collect(Collectors.joining("\n"));
      writer.write(formattedText);
      System.out.println("File created and content written successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void exportSetToTXT(List<Set> sets) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("sets_list.txt"))) {
      String formattedText = sets.stream().filter(set -> !set.getTitle().isEmpty()).map(Set::getNumberWithTitle).collect(Collectors.joining("\n"));
      writer.write(formattedText);
      System.out.println("File created and content written successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void exportToTXT(List<Canto> cantos) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("cantos_list.txt"))) {
      String formattedText = cantos.stream().filter(canto -> !canto.getCantoName().isEmpty()).map(Canto::getNumberTitleAndSheetCounter).collect(Collectors.joining("\n"));
      writer.write(formattedText);
      System.out.println("File created and content written successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void exportSheetsToTXT(List<Canto> cantos) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("cantos_sheets.txt"))) {
      String formattedText = cantos.stream().filter(canto -> !canto.getCantoName().isEmpty()).map(Canto::getNumberAndSheets).collect(Collectors.joining("\n"));
      writer.write(formattedText);
      System.out.println("File created and content written successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
