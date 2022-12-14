package dev.niziolek.pdfsearch;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.BulkOperations;
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
  public void addDocumentIndex(String documentId, Map<String, Map<Integer, List<Integer>>> documentIndex) {
    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Word.class);

    for (Map.Entry<String, Map<Integer, List<Integer>>> wordEntry : documentIndex.entrySet()) {
      String word = wordEntry.getKey();
      Map<Integer, List<Integer>> occurrences = wordEntry.getValue();

      // add a word entry if it does not exist
      Query queryWord = new Query().addCriteria(Criteria.where("word").is(word));
      if (!mongoTemplate.exists(queryWord, Word.class)) {
        bulkOps.insert(new Word(word));
      }

      // add occurrences
      Update addOccurrence = new Update().set("occurrences." + documentId, occurrences);
      bulkOps.updateOne(queryWord, addOccurrence);
    }

    bulkOps.execute();
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
