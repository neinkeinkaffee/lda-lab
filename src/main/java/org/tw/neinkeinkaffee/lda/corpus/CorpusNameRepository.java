package org.tw.neinkeinkaffee.lda.corpus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpusNameRepository extends MongoRepository<CorpusName, String> {
    public boolean existsByName(String name);
    void deleteByName(String name);
}
