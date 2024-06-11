package com.example.benedictus.src.main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Set;

public class Main {

  public static List<Piece> pieces = new ArrayList<>();
  public static final Scanner input = new Scanner(System.in);
  public static void main(String[] args) {

    readData("C:\\Users\\Monika\\Desktop\\parafia\\benedictus\\TEXT", new CantoConverter());
    readData("C:\\Users\\Monika\\Desktop\\parafia\\benedictus\\SETS", new SetConverter());


    // create cantos list
   // cantos.sort(Comparator.comparing(Canto::getCantoName));
    // sub 600-699 - psalms
    //cantos = cantos.stream().filter(canto -> canto.getNumber() > 699 || canto.getNumber() < 600).toList();

   // cantos = cantos.stream().filter(canto -> canto.getNumber() < 817 || canto.getNumber() > 856).toList();
   // cantos = cantos.stream().filter(canto -> canto.getNumber() < 705 || canto.getNumber() > 723).toList();
   // cantos = cantos.stream().filter(canto -> canto.getNumber() < 775 || canto.getNumber() > 781).toList();
   // cantos = cantos.stream().filter(canto -> canto.getNumber()==Integer.parseInt(canto.getFileName().split("\\.")[0])).toList();
   // cantos = cantos.subList(27, cantos.size());
    PdfWriter.exportToPDF(filterCantos(pieces));
    TxtWriter.exportToTXT(filterCantos(pieces));
    TxtWriter.exportSheetsToTXT(filterCantos(pieces));
    TxtWriter.exportToTXT(filterCantos(pieces), "cantos.txt");

    //TxtWriter.exportSetToTXT(filterSets(pieces));

    System.out.println("Wybierz: ");
    System.out.println("C - aby dodać nową pieśń");
    System.out.println("Z - aby dodać nowy zestaw");
    System.out.println("E - aby zakończyć");

    String userChoice = input.nextLine();

    if (Objects.equals(userChoice, "D")) {
      CantoFormatter cantoFormatter = new CantoFormatter();
      System.out.println("Podaj NAZWĘ pieśni: ");
      String name = input.nextLine();
      System.out.println("Podaj TEKST pieśni: ");
      StringBuilder text = new StringBuilder();
      while (input.hasNextLine()) {
        String line = input.nextLine();
        if(line.equals("e"))
          break;
        text.append(line);
      }
      System.out.println();
      Canto canto = cantoFormatter.createCanto(name, text.toString(), 56);
      canto.generateTXT();
      System.out.println("Pomyślnie utworzono pieśń " + canto.getSheets() + " " + name + " " + text);
    }
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

    File folder = new File(folderPath);

    if (folder.exists() && folder.isDirectory()) {
      File[] listOfFiles = folder.listFiles();

      for (File file : listOfFiles) {
        if (file.isFile()) {
          String filePath = file.getAbsolutePath();
          String content = cantosReader.getCanto(filePath);
          Piece piece = converter.convert(content, file.getName());
          System.out.println(piece);
          pieces.add(piece);
        }
      }
    } else {
      System.out.println("Podana ścieżka nie jest katalogiem lub nie istnieje.");
    }
  }
}
