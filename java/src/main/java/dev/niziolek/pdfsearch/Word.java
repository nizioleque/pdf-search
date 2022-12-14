package dev.niziolek.pdfsearch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Word {
  @Id
  String id;

  String word;
  HashMap<String, List<Integer>> occurrences;

  public Word(String word) {
    this.word = word;
    this.occurrences = new HashMap<>();
  }

  static String parse(String word) {
    word = word.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "");
    if (word.equals("")) return null;
    return word.toLowerCase();
  }
}
