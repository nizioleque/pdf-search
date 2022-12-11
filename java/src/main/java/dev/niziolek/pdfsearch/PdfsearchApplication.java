package dev.niziolek.pdfsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfsearchApplication {
  private DocumentRepository documentRepository;
  private WordRepository wordRepository;

  public static void main(String[] args) {
    SpringApplication.run(PdfsearchApplication.class, args);
  }
}
