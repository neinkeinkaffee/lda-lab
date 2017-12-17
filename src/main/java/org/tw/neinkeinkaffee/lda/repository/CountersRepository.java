package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.lda.LdaCounters;

@Repository
public interface CountersRepository extends MongoRepository<LdaCounters, String> {
    public LdaCounters findByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}
