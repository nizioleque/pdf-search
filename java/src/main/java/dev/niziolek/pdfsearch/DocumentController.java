package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/document")
@AllArgsConstructor
public class DocumentController {

  private final DocumentService documentService;
  private final IndexService indexService;

  @GetMapping
  public List<Document> fetchAllDocuments() {
    return documentService.getAllDocuments();
  }

  @PostMapping
  public String addDocument(@RequestParam String title,
                            @RequestParam String author,
                            @RequestParam MultipartFile file
  ) throws IOException {
    Document document = documentService.addDocument(title, author, file);
    indexService.indexDocument(document);
    return document.id;
  }

  @DeleteMapping("/{id}")
  public boolean deleteDocument(@PathVariable String id) {
    documentService.deleteDocument(id);
    indexService.removeDocumentIndex(id);
    return true;
  }

}
