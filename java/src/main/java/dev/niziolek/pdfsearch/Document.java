package dev.niziolek.pdfsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Document {
  @Id
  String id;

  String title;
  String author;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  ObjectId content;

  public Document(String title, String author, ObjectId content) {
    this.title = title;
    this.author = author;
    this.content = content;
  }
}
