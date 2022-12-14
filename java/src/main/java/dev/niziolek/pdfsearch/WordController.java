package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/word")
@AllArgsConstructor
public class WordController {
  private final WordService wordService;

  @GetMapping("/{word}")
  public Map<String, List<Integer>> fetchWord(@PathVariable String word) {
    return wordService.getPostingsByWord(word);
  }
}
