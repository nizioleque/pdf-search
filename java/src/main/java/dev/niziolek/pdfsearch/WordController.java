package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/word")
@AllArgsConstructor
public class WordController {

  private final WordService wordService;

  @GetMapping
  public List<Word> fetchAllWords() {
    return wordService.getAllWords();
  }
}
