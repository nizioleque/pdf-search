package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class DocumentService {

  @Autowired
  private GridFsTemplate gridFsTemplate;

  private final DocumentRepository documentRepository;

  public List<Document> getAllDocuments() {
    return documentRepository.findAll();
  }

  public Document addDocument(String title, String author, MultipartFile file) throws IOException {
    ObjectId fileId = gridFsTemplate.store(
            file.getInputStream(), file.getName(), file.getContentType());
    return documentRepository.insert(new Document(title, author, fileId));
  }

}
