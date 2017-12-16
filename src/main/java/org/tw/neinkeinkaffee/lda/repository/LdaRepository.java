package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;

@Repository
public interface LdaRepository extends MongoRepository<Lda, String> {
	public Lda findByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}