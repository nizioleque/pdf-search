package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class WordRepositoryImpl implements CustomWordRepository {
  private final MongoTemplate mongoTemplate;

  @Override
  public void addDocumentIndex(String documentId, HashMap<String, List<Integer>> documentIndex) {
    for (Map.Entry<String, List<Integer>> wordEntry : documentIndex.entrySet()) {
      String word = wordEntry.getKey();
      List<Integer> occurrences = wordEntry.getValue();

      // add a word entry if it does not exist
      Query queryWord = new Query().addCriteria(Criteria.where("word").is(word));
      if (!mongoTemplate.exists(queryWord, Word.class)) {
        mongoTemplate.save(new Word(word));
      }

      // add occurrences
      Update addOccurrence = new Update().set("occurrences." + documentId, occurrences);
      mongoTemplate.updateFirst(queryWord, addOccurrence, Word.class);
    }
  }

  @Override
  public void removeDocumentIndex(String documentId) {
    // remove the document id field from all documents
    Update removeDocumentField = new Update().unset("occurrences." + documentId);
    mongoTemplate.updateMulti(new Query(), removeDocumentField, Word.class);

    // remove words with empty document objects
    Query emptyWords = new Query().addCriteria(Criteria.where("occurrences").is(new HashMap<String, List<Integer>>()));
    mongoTemplate.remove(emptyWords, Word.class);
  }


}
