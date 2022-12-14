package dev.niziolek.pdfsearch;

public interface CustomWordRepository {
  boolean addOccurrence(String word, String documentId, int pageNumber);

  void removeDocument(String documentId);
}
