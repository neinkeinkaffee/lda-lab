package org.tw.neinkeinkaffee.lda.lda;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.lda.Lda;

@Repository
public interface LdaRepository extends MongoRepository<Lda, String> {
	public Lda findByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}