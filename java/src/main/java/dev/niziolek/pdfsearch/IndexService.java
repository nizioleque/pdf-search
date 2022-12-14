package dev.niziolek.pdfsearch;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class IndexService {

  private final WordRepository wordRepository;
  private final DocumentService documentService;
  private final GridFsTemplate gridFsTemplate;
  private final MongoDatabaseFactory mongoDatabaseFactory;

  public void indexDocument(Document document) {

  }

  public void removeDocumentIndex(Document document) {
    
  }

  public void addWordOccurrence(String word, String documentId, int pageNumber) {
    Optional<Word> databaseWord = wordRepository.findByWord(word);
    Word currentWord = databaseWord.orElseGet(() -> new Word(word));

    List<Integer> pageNumbers = currentWord.getPostings().get(documentId);
    if (pageNumbers == null) {
      pageNumbers = new ArrayList<>();
      currentWord.getPostings().put(documentId, pageNumbers);
    }
    pageNumbers.add(pageNumber);

    wordRepository.save(currentWord);
  }

  public void recalculateIndex() {
    System.out.println("Recalculating inverted index");
    long startTime = System.nanoTime();
    wordRepository.deleteAll();
    List<Document> documents = documentService.getAllDocuments();
    GridFSBucket gridFs = getGridFs();

    for (Document document : documents) {
      GridFSFile pdfFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(document.content)));
      GridFsResource pdfResource = new GridFsResource(pdfFile, gridFs.openDownloadStream(pdfFile.getObjectId()));
      PDDocument pdfDocument;

      try {
        pdfDocument = PDDocument.load(pdfResource.getInputStream());
      } catch (IOException e) {
        System.out.println("Could not read PDF contents of document " + document.title + ", " + e.getMessage());
        return;
      }

      for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
        try {
          PDFTextStripper stripper = new PDFTextStripper();
          stripper.setStartPage(i);
          stripper.setEndPage(i);
          String text = stripper.getText(pdfDocument);
          analyzePage(document.getId(), text, i);
        } catch (IOException e) {
          System.out.println("Could not read PDF contents of document " + document.title + ", " + e.getMessage());
        }
      }
    }

    long endTime = System.nanoTime();
    System.out.println("Finished!, time: " + (endTime - startTime) / 1000000 + "ms");
  }

  private void analyzePage(String documentId, String pageContent, int pageNumber) {
    String[] words = pageContent.split(" ");
    for (String word : words) {
      String parsedWord = Word.parse(word);
      if (parsedWord != null) addWordOccurrence(parsedWord, documentId, pageNumber);
    }
  }

  private GridFSBucket getGridFs() {
    MongoDatabase db = mongoDatabaseFactory.getMongoDatabase();
    return GridFSBuckets.create(db);
  }
}
