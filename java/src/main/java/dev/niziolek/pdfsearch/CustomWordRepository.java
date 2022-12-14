package dev.niziolek.pdfsearch;

import java.util.HashMap;
import java.util.List;

public interface CustomWordRepository {
  void addDocumentIndex(String documentId, HashMap<String, List<Integer>> documentIndex);

  void removeDocumentIndex(String documentId);
}
