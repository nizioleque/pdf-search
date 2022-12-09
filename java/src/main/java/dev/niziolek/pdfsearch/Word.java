package dev.niziolek.pdfsearch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
public class Word {
  @Id
  String id;

  String word;
  List<Posting> postings;

  public Word(String word, List<Posting> postings) {
    this.word = word;
    this.postings = postings;
  }
}
