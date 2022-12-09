package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/document")
@AllArgsConstructor
public class DocumentController {

  private final DocumentService documentService;

  @GetMapping
  public List<Document> fetchAllDocuments() {
    return documentService.getAllDocuments();
  }

}
