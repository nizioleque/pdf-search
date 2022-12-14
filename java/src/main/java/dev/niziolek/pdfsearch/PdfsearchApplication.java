package dev.niziolek.pdfsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PdfsearchApplication {
  private DocumentRepository documentRepository;
  private WordRepository wordRepository;

  public static void main(String[] args) {
    SpringApplication.run(PdfsearchApplication.class, args);
  }
}
