package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WordService {

  private final WordRepository wordRepository;

  public List<Word> getAllWords() {
    return wordRepository.findAll();
  }
  
}
