package org.tw.neinkeinkaffee.lda.corpus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorpusDocumentRepository extends MongoRepository<CorpusDocument, String> {
    public List<CorpusDocument> findAllByCorpusName(String corpusName);
    void deleteAllByCorpusName(String corpusName);
}
