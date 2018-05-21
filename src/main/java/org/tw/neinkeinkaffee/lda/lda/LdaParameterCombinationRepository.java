package org.tw.neinkeinkaffee.lda.lda;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.dto.lda.LdaParameterCombination;

import java.util.List;

@Repository
public interface LdaParameterCombinationRepository extends MongoRepository<LdaParameterCombination, String> {
    public List<LdaParameterCombination> findAll();

    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
}
