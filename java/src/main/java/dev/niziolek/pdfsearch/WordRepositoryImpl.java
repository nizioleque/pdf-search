package dev.niziolek.pdfsearch;

import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class WordRepositoryImpl implements CustomWordRepository {
  private final MongoTemplate mongoTemplate;

  @Override
  public boolean addOccurrence(String word, String documentId, int pageNumber) {
    // add a word entry if it does not exist
    Query queryWord = new Query().addCriteria(Criteria.where("word").is(word));
    if (!mongoTemplate.exists(queryWord, Word.class)) {
      mongoTemplate.save(new Word(word));
    }

    // add occurrence
    Update addOccurrence = new Update().addToSet("occurrences." + documentId, pageNumber);
    UpdateResult updateResult = mongoTemplate.updateFirst(queryWord, addOccurrence, Word.class);

    return updateResult.getModifiedCount() == 1;
  }

  @Override
  public void removeDocument(String documentId) {
    // remove the document id field from all documents
    Update removeDocumentField = new Update().unset("occurrences." + documentId);
    mongoTemplate.updateMulti(new Query(), removeDocumentField, Word.class);

    // remove words with empty document objects
    Query emptyWords = new Query().addCriteria(Criteria.where("occurrences").is(new HashMap<String, List<Integer>>()));
    mongoTemplate.remove(emptyWords, Word.class);
  }


}
