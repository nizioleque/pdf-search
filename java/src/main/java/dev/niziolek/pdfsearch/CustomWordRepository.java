package dev.niziolek.pdfsearch;

import java.util.List;
import java.util.Map;

public interface CustomWordRepository {
  void addDocumentIndex(String documentId, Map<String, Map<Integer, List<Integer>>> documentIndex);

  void removeDocumentIndex(String documentId);
}
