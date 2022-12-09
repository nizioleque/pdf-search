package dev.niziolek.pdfsearch;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class PdfsearchApplication implements CommandLineRunner {

  @Autowired
  private DocumentRepository documentRepository;

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

  @Override
  public void run(String... args) throws Exception {
    documentRepository.deleteAll();

    // save a couple of customers
    documentRepository.save(new Document("Document3", "Author1"));
    documentRepository.save(new Document("Document4", "Author2"));

    // fetch all customers
    System.out.println("Customers found with findAll():");
    System.out.println("-------------------------------");
    for (Document document : documentRepository.findAll()) {
      System.out.println(document);
    }
    System.out.println();

    // fetch an individual customer
    System.out.println("Customer found with findByFirstName('Alice'):");
    System.out.println("--------------------------------");
    System.out.println(documentRepository.findByTitle("Document3"));
  }
}
