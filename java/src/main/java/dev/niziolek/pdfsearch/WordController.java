package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/word")
@AllArgsConstructor
public class WordController {
  private final WordService wordService;

  @GetMapping("/{word}")
  public List<SearchResult> fetchWord(@PathVariable String word) {
    return wordService.getOccurrencesByWord(word).stream().limit(50).toList();
  }
}
