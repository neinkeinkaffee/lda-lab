package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.lda.LdaDocument;

import java.util.List;

@Repository
public interface LdaDocumentRepository extends MongoRepository<LdaDocument, String> {
    public List<LdaDocument> findByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}
