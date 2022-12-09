package dev.niziolek.pdfsearch;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DocumentRepository extends MongoRepository<Document, String> {
  Optional<Document> findByTitle(String title);
}
