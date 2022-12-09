package dev.niziolek.pdfsearch;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Document {
  @Id
  ObjectId id;

  String title;
  String author;
  ObjectId content;

  public Document(String title, String author, ObjectId content) {
    this.title = title;
    this.author = author;
    this.content = content;
  }
}
