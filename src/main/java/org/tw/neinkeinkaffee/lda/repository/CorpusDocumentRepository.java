package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;

import java.util.List;

@Repository
public interface CorpusDocumentRepository extends MongoRepository<CorpusDocument, String> {
    public List<CorpusDocument> findAllByCorpusName(String corpusName);
    void deleteAllByCorpusName(String corpusName);
}
