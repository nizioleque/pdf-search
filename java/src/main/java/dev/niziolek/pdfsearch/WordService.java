package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WordService {
  private final WordRepository wordRepository;

  public Map<String, List<Integer>> getWord(String word) {
    Optional<Word> result = wordRepository.findByWord(word);
    if (result.isPresent()) {
      return result.get().getPostings();
    }
    return new HashMap<>();
  }
}
