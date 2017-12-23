package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.repository.ContentWordRepository;
import org.tw.neinkeinkaffee.lda.repository.DocumentRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(final DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DtoDocument> fetchAllBy(String corpusName, int numberOfTopics, Instant timestamp) {
        return documentRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public DtoDocument fetchBy(String corpusName, int numberOfTopics, Instant timestamp, String title) {
        return documentRepository.findByCorpusNameAndNumberOfTopicsAndTimestampAndTitle(corpusName, numberOfTopics, timestamp, title);
    }
}
