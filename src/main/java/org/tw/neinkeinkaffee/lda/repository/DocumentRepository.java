package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<DtoDocument, String> {
    public List<DtoDocument> findAllByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, Instant timestamp);

    public DtoDocument findByCorpusNameAndNumberOfTopicsAndTimestampAndTitle(String corpusName, int numberOfTopics, Instant timestamp, String title);

    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, Timestamp timestamp);
}
