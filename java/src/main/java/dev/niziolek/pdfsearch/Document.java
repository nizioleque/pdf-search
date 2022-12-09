package dev.niziolek.pdfsearch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Document {
  @Id
  String id;

  String title;
  String author;

  public Document(String title, String author) {
    this.title = title;
    this.author = author;
  }
}
