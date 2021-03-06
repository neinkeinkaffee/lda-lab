package org.tw.neinkeinkaffee.lda.dto.word;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.dto.word.ContentWord;

import java.util.List;

@Repository
public interface ContentWordRepository extends MongoRepository<ContentWord, String> {
    public List<ContentWord> findAllByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);

    public ContentWord findByCorpusNameAndNumberOfTopicsAndTimestampAndLemma(String corpusName, int numberOfTopics, String timestamp, String lemma);

    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
}
