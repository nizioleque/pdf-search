package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DocumentService {

  private final GridFsTemplate gridFsTemplate;
  private final DocumentRepository documentRepository;

  public List<Document> getAllDocuments() {
    return documentRepository.findAll();
  }

  public Document addDocument(String title, String author, MultipartFile file) throws IOException {
    ObjectId fileId = gridFsTemplate.store(
            file.getInputStream(), file.getName(), file.getContentType());
    return documentRepository.save(new Document(title, author, fileId));
  }

  public void deleteDocument(String id) {
    Optional<Document> document = documentRepository.findById(id);
    if (document.isEmpty()) return;
    gridFsTemplate.delete(new Query().addCriteria(Criteria.where("_id").is(document.get().content)));
    documentRepository.deleteById(id);
  }

}
