package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

import java.util.List;

@Repository
public interface ContentWordRepository extends MongoRepository<ContentWord, String> {
    public List<ContentWord> findAllByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);

    public ContentWord findByCorpusNameAndNumberOfTopicsAndLemma(String corpusName, int numberOfTopics, String lemma);

    public Long deleteByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}
