package dev.niziolek.pdfsearch;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends MongoRepository<Word, String>, CustomWordRepository {
  Optional<Word> findByWord(String word);

  List<Word> findByWordStartsWith(String wordStart);
}
