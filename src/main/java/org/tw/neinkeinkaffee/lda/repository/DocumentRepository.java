package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<DtoDocument, String> {
    public List<DtoDocument> findAllByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);

    public DtoDocument findByCorpusNameAndNumberOfTopicsAndTitle(String corpusName, int numberOfTopics, String title);
}
