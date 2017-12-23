package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public interface ContentWordRepository extends MongoRepository<ContentWord, String> {
    public List<ContentWord> findAllByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, Instant timestamp);

    public ContentWord findByCorpusNameAndNumberOfTopicsAndTimestampAndLemma(String corpusName, int numberOfTopics, Instant timestamp, String lemma);

    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, Timestamp timestamp);
}
