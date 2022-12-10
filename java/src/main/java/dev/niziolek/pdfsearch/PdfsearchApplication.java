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

  @Autowired
  private WordRepository wordRepository;

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
  public void run(String... args) {
//    documentRepository.deleteAll();
//    wordRepository.deleteAll();
//
//    // save a couple of customers
//    documentRepository.save(new Document("Document3", "Author1", null));
//    documentRepository.save(new Document("Document4", "Author2", null));
//    wordRepository.save(new Word("Word1", List.of()));
//    wordRepository.save(new Word("Word2", List.of()));

    // fetch all customers
    System.out.println("Customers found with findAll():");
    System.out.println("-------------------------------");
    for (Document document : documentRepository.findAll()) {
      System.out.println(document);
    }
    for (Word word : wordRepository.findAll()) {
      System.out.println(word);
    }
    System.out.println();

    // fetch an individual customer
//    System.out.println("Customer found with findByFirstName('Alice'):");
//    System.out.println("--------------------------------");
//    System.out.println(documentRepository.findByTitle("Document3"));
//    System.out.println(wordRepository.findByWord("WordDDD"));

  }
}
