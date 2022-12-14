package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class WordService {
  private final WordRepository wordRepository;

  public Map<String, List<Integer>> getOccurrencesByWord(String query) {
    String[] queryWords = query.split(" ");

    Map<String, List<Integer>> resultOccurrences = new HashMap<>();

//    for (int i = 0; i < queryWords.length; i++) {
//      boolean first = i == 0;
//      boolean last = i == queryWords.length - 1;
//
//      Map<String, List<Integer>> currentOccurrences;
//
//      if (last) {
//        currentOccurrences = getLastWordOccurrences(queryWords[i]);
//      } else {
//        Optional<Word> result = wordRepository.findByWord(queryWords[i]);
//        if (result.isEmpty()) continue;
//        currentOccurrences = result.get().getOccurrences();
//      }
//
//      if (first) {
//        resultOccurrences.putAll(currentOccurrences);
//        continue;
//      }
//
//      for (String resultKey : resultOccurrences.keySet()) {
//        if (currentOccurrences.containsKey(resultKey)) {
//          // remove array entries that do not exist in currentOccurrences
//          resultOccurrences.put(
//                  resultKey,
//                  currentOccurrences
//                          .get(resultKey)
//                          .stream()
//                          .filter(resultOccurrences.get(resultKey)::contains)
//                          .collect(Collectors.toList()));
//        } else {
//          // remove key from result occurrences
//          resultOccurrences.remove(resultKey);
//        }
//      }
//    }

    return resultOccurrences;
  }

//  private Map<String, List<Integer>> getLastWordOccurrences(String lastWord) {
//    List<Word> words = wordRepository.findByWordStartsWith(lastWord);
//    HashMap<String, List<Integer>> occurrences = new HashMap<>();
//
//    for (Word word : words) {
//      for (Map.Entry<String, List<Integer>> wordEntry : word.getOccurrences().entrySet()) {
//        if (!occurrences.containsKey(wordEntry.getKey())) {
//          occurrences.put(wordEntry.getKey(), wordEntry.getValue());
//        } else {
//          occurrences.put(
//                  wordEntry.getKey(),
//                  Stream.concat(
//                                  occurrences.get(wordEntry.getKey()).stream(),
//                                  wordEntry.getValue().stream()
//                          )
//                          .distinct()
//                          .sorted()
//                          .toList());
//        }
//      }
//    }
//    return occurrences;
//  }
}
