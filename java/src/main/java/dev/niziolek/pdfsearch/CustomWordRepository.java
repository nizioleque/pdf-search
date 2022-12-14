package dev.niziolek.pdfsearch;

public interface CustomWordRepository {
  boolean addPosting(String word, String documentId, int pageNumber);

  void removeDocument(String documentId);
}
