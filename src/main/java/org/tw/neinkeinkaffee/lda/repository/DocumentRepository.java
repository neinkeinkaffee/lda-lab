package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<DtoDocument, String> {
    public List<DtoDocument> findAllByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
    public DtoDocument findByCorpusNameAndNumberOfTopicsAndTimestampAndTitle(String corpusName, int numberOfTopics, String timestamp, String title);
    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
}
