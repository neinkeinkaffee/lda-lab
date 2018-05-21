package org.tw.neinkeinkaffee.lda.dto.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(final DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DtoDocument> fetchAllBy(String corpusName, int numberOfTopics, String timestamp) {
        return documentRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public DtoDocument fetchBy(String corpusName, int numberOfTopics, String timestamp, String title) {
        return documentRepository.findByCorpusNameAndNumberOfTopicsAndTimestampAndTitle(corpusName, numberOfTopics, timestamp, title);
    }
}
