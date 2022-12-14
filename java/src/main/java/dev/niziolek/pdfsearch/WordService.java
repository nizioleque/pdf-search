package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class WordService {
  private final WordRepository wordRepository;

  public Map<String, List<Integer>> getPostingsByWord(String query) {
    String[] queryWords = query.split(" ");

    System.out.println(queryWords);
    Map<String, List<Integer>> resultPostings = new HashMap<>();

    for (int i = 0; i < queryWords.length; i++) {
      boolean first = i == 0;
      boolean last = i == queryWords.length - 1;

      Map<String, List<Integer>> currentPostings;

      if (last) {
        currentPostings = getLastWordPostings(queryWords[i]);
      } else {
        Optional<Word> result = wordRepository.findByWord(queryWords[i]);
        if (result.isEmpty()) continue;
        currentPostings = result.get().getPostings();
      }

      if (first) {
        resultPostings.putAll(currentPostings);
        continue;
      }

      for (String resultKey : resultPostings.keySet()) {
        if (currentPostings.containsKey(resultKey)) {
          // remove array entries that do not exist in currentPostings
          resultPostings.put(
                  resultKey,
                  currentPostings
                          .get(resultKey)
                          .stream()
                          .filter(resultPostings.get(resultKey)::contains)
                          .collect(Collectors.toList()));
        } else {
          // remove key from result postings
          resultPostings.remove(resultKey);
        }

        System.out.println(currentPostings);
        System.out.println(resultPostings);
      }
    }

    return resultPostings;
  }

  private Map<String, List<Integer>> getLastWordPostings(String lastWord) {
    List<Word> words = wordRepository.findByWordStartsWith(lastWord);
    HashMap<String, List<Integer>> postings = new HashMap<>();

    for (Word word : words) {
      for (Map.Entry<String, List<Integer>> wordEntry : word.getPostings().entrySet()) {
        if (!postings.containsKey(wordEntry.getKey())) {
          postings.put(wordEntry.getKey(), wordEntry.getValue());
        } else {
          postings.put(
                  wordEntry.getKey(),
                  Stream.concat(
                                  postings.get(wordEntry.getKey()).stream(),
                                  wordEntry.getValue().stream()
                          )
                          .distinct()
                          .sorted()
                          .toList());
        }
      }
    }
    return postings;
  }
}
