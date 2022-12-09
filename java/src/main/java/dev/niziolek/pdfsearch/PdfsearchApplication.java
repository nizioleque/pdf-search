package dev.niziolek.pdfsearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class PdfsearchApplication {
  public static void main(String[] args) {
    File pdfFile = new File("../pdfs/konstytucja.pdf");
    try {
      PDDocument doc = PDDocument.load(pdfFile);
      String text = new PDFTextStripper().getText(doc);
      System.out.println("Text in PDF: " + text.substring(0, 10000));
    } catch (Exception ignored) {
      System.out.println("Could not read PDF contents");
    }

    SpringApplication.run(PdfsearchApplication.class, args);
  }

}
