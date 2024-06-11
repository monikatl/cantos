package com.example.benedictus.src.main.java;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfWriter {
  public static void exportToPDF(List<Canto> cantos) {
    System.setProperty("sun.jnu.encoding", "Cp1250");
    try (PDDocument document = new PDDocument()) {
      PDType0Font font = PDType0Font.load(document, new File("C:\\Users\\Monika\\Desktop\\parafia\\OpenSans-Regular.ttf"));
      float fontSize = 14;
      float margin = 20;
      int cantosPerPage = 42;
      int cantoCount = 0;
      PDPage page = new PDPage();
      document.addPage(page);
      PDPageContentStream contentStream = new PDPageContentStream(document, page);
      contentStream.setFont(font, fontSize);
      float lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
      contentStream.moveTo(100, page.getMediaBox().getHeight() - margin);

      for (Canto canto : cantos) {
        if (cantoCount > 0 && cantoCount % cantosPerPage == 0) {
          contentStream.close();
          page = new PDPage();
          document.addPage(page);
          contentStream = new PDPageContentStream(document, page);
          contentStream.setFont(font, fontSize);
          lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
          contentStream.moveTo(100, page.getMediaBox().getHeight() - margin);
        }

        float y = page.getMediaBox().getHeight() - margin - (cantoCount % cantosPerPage * lineHeight);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, y);
        contentStream.showText(canto.getNumberAndTitleSpaces());
        contentStream.endText();
        cantoCount++;
      }

      contentStream.close();
      document.save("cantos.pdf");
      System.out.println("The PDF file has been exported successfully.");
    } catch (IOException e) {
      System.err.println("An error occurred while creating the PDF file: " + e.getMessage());
    }
  }
}
