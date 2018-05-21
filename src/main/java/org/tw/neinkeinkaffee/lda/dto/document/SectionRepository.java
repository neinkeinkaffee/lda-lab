package org.tw.neinkeinkaffee.lda.dto.document;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.dto.document.DtoDocument;

import java.util.List;

@Repository
public interface SectionRepository extends MongoRepository<DtoDocument.DtoSection, String> {
    public List<DtoDocument.DtoSection> findAllByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
}
