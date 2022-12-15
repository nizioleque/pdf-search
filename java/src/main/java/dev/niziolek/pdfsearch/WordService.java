package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WordService {
  private final WordRepository wordRepository;

  public List<SearchResult> getOccurrencesByWord(String query) {
    String[] queryWords = query.split(" ");
    List<SearchResult> potentialResults = new ArrayList<>();

    if (queryWords.length < 1) return List.of();

    // special case for one word
    if (queryWords.length == 1) {
      List<Word> firstWords = wordRepository.findByWordStartsWith(queryWords[0]);

      for (Word firstWord : firstWords) {
        var firstWordOccurrences = firstWord.getOccurrences();
        for (var documentOccurrences : firstWordOccurrences.entrySet()) {
          for (var pageOccurrences : documentOccurrences.getValue().entrySet()) {
            for (int pageOccurrence : pageOccurrences.getValue()) {
              String documentId = documentOccurrences.getKey();
              int pageNumber = pageOccurrences.getKey();
              SearchResult searchResult = new SearchResult(documentId, pageNumber, pageOccurrence, firstWord.getWord());
              potentialResults.add(searchResult);
            }
          }
        }
      }
      
      return potentialResults;
    }

    // add all first word occurrences as potential search results
    Optional<Word> firstWordResult = wordRepository.findByWord(queryWords[0]);
    if (firstWordResult.isEmpty()) return List.of();
    var firstWordOccurrences = firstWordResult.get().getOccurrences();
    for (var documentOccurrences : firstWordOccurrences.entrySet()) {
      for (var pageOccurrences : documentOccurrences.getValue().entrySet()) {
        for (int pageOccurrence : pageOccurrences.getValue()) {
          String documentId = documentOccurrences.getKey();
          int pageNumber = pageOccurrences.getKey();
          SearchResult searchResult = new SearchResult(documentId, pageNumber, pageOccurrence, null);
          potentialResults.add(searchResult);
        }
      }
    }

    // filter solutions
    for (int i = 1; i < queryWords.length - 1; i++) {
      Optional<Word> result = wordRepository.findByWord(queryWords[i]);
      if (result.isEmpty()) {
        potentialResults.clear();
        break;
      }

      var currentOccurrences = result.get().getOccurrences();

      for (Iterator<SearchResult> itr = potentialResults.iterator(); itr.hasNext(); ) {
        SearchResult potentialResult = itr.next();
        boolean remove = false;

        var documentOccurrences = currentOccurrences.get(potentialResult.documentId);
        if (documentOccurrences == null) remove = true;

        if (!remove) {
          List<Integer> pageOccurrences = documentOccurrences.get(potentialResult.pageNumber);
          if (pageOccurrences == null || !pageOccurrences.contains(potentialResult.firstWordIndex + i)) remove = true;
        }

        if (remove) {
          itr.remove();
        }
      }
    }

    // last word
    List<Word> lastWords = wordRepository.findByWordStartsWith(queryWords[queryWords.length - 1]);
    for (Iterator<SearchResult> itr = potentialResults.iterator(); itr.hasNext(); ) {
      SearchResult potentialResult = itr.next();
      boolean remove = true;

      for (Word lastWord : lastWords) {
        var documentOccurrences = lastWord.getOccurrences().get(potentialResult.documentId);
        if (documentOccurrences == null) continue;

        List<Integer> pageOccurrences = documentOccurrences.get(potentialResult.pageNumber);
        if (pageOccurrences == null) continue;

        if (pageOccurrences.contains(potentialResult.firstWordIndex + queryWords.length - 1)) {
          potentialResult.lastWord = lastWord.getWord();
          remove = false;
        }
      }

      if (remove) {
        itr.remove();
      }
    }

    return potentialResults;
  }
}
