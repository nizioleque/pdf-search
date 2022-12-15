package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult {
  String documentId;
  int pageNumber;
  int firstWordIndex;
  String lastWord;
}
