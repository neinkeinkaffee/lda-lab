package org.tw.neinkeinkaffee.lda.repository;

import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.CorpusName;

@Repository
public interface CorpusNameRepository extends MongoRepository<CorpusName, String> {
}
