package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoSection;
import org.tw.neinkeinkaffee.lda.model.dto.DtoVolume;
import org.tw.neinkeinkaffee.lda.repository.DocumentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
