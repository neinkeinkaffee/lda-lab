package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;

@Repository
public interface CorpusRepository extends MongoRepository<Corpus, String> {
    public Corpus findByName(String name);
}
