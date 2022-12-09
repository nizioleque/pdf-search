package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DocumentService {

  private final DocumentRepository documentRepository;

  public List<Document> getAllDocuments() {
    return documentRepository.findAll();
  }

}
