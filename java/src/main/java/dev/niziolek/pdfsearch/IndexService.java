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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class IndexService {
  private final WordRepository wordRepository;
  private final DocumentService documentService;
  private final GridFsTemplate gridFsTemplate;
  private final MongoDatabaseFactory mongoDatabaseFactory;

  @Async
  public void indexDocument(Document document) {
    System.out.println("Adding document \"" + document.title + "\"");
    long startTime = System.nanoTime();

    GridFSFile pdfFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(document.content)));
    GridFsResource pdfResource = new GridFsResource(pdfFile, getGridFs().openDownloadStream(pdfFile.getObjectId()));
    PDDocument pdfDocument;

    try {
      pdfDocument = PDDocument.load(pdfResource.getInputStream());
    } catch (IOException e) {
      System.out.println("Could not read PDF contents of document " + document.title + ", " + e.getMessage());
      return;
    }

    Map<String, Map<Integer, List<Integer>>> documentIndex = new HashMap<>();

    for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
      try {
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(i);
        stripper.setEndPage(i);
        String text = stripper.getText(pdfDocument);
        analyzePage(document.getId(), text, i, documentIndex);
      } catch (IOException e) {
        System.out.println("Could not read PDF contents of document " + document.title + ", " + e.getMessage());
      }
    }

    wordRepository.addDocumentIndex(document.id, documentIndex);
    documentService.updateDocumentState(document.id, DocumentStatus.ADDED);

    long endTime = System.nanoTime();
    System.out.println("Finished adding document \"" + document.title + "\"!, time: " + (endTime - startTime) / 1000000000 + " s");
  }

  public void removeDocumentIndex(String id) {
    wordRepository.removeDocumentIndex(id);
  }

  private void analyzePage(String documentId, String pageContent, int pageNumber, Map<String, Map<Integer, List<Integer>>> documentIndex) {
    String[] words = pageContent.split(" ");
    int wordIndex = 0;
    for (String word : words) {
      String parsedWord = Word.parse(word);

      if (parsedWord != null) {
        if (!documentIndex.containsKey(parsedWord)) documentIndex.put(parsedWord, new HashMap<>());
        if (!documentIndex.get(parsedWord).containsKey(pageNumber))
          documentIndex.get(parsedWord).put(pageNumber, new LinkedList<>());

        documentIndex.get(parsedWord).get(pageNumber).add(wordIndex);
        wordIndex++;
      }

    }
  }

  private GridFSBucket getGridFs() {
    MongoDatabase db = mongoDatabaseFactory.getMongoDatabase();
    return GridFSBuckets.create(db);
  }
}
