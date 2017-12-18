package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.LdaParameterCombination;

import java.util.List;

@Repository
public interface LdaParameterCombinationRepository extends MongoRepository<LdaParameterCombination, String> {
    public List<LdaParameterCombination> findAll();

    public Long deleteByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}
