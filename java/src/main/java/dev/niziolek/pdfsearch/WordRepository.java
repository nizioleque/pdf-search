package dev.niziolek.pdfsearch;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepository extends MongoRepository<Word, String> {
  public Word findByWord(String word);
}
