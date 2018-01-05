package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.*;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.repository.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.sort;
import static java.util.stream.Collectors.groupingBy;

@Service
public class LdaService {
    // TODO: Should encapsulate the repositories for the Corpus and for the Lda in super repositories that are injected into the CorpusService and LdaService (LdaService can also get a corpusRepository instead of the corpusService)
    private TopicRepository topicRepository;
    private ContentWordRepository contentWordRepository;
    private DocumentRepository documentRepository;
    private CorpusService corpusService;
    private LdaParameterCombinationRepository ldaParameterCombinationRepository;
    private SectionRepository sectionRepository;

    @Autowired
    public LdaService(final TopicRepository topicRepository,
                      final ContentWordRepository contentWordRepository,
                      final DocumentRepository documentRepository,
                      final CorpusService corpusService,
                      final LdaParameterCombinationRepository ldaParameterCombinationRepository,
                      final SectionRepository sectionRepository) {
        this.topicRepository = topicRepository;
        this.contentWordRepository = contentWordRepository;
        this.documentRepository = documentRepository;
        this.corpusService = corpusService;
        this.ldaParameterCombinationRepository = ldaParameterCombinationRepository;
        this.sectionRepository = sectionRepository;
    }

    public DtoLda fetchBy(String corpusName, int numberOfTopics, String timestamp) {
        // TODO: check in parametercombinationsrepository whether model exists
        List<Topic> topics = topicRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
        List<ContentWord> words = contentWordRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
        List<DtoDocument> documents = documentRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
        return DtoLda.builder()
            .corpusName(corpusName)
            .numberOfTopics(numberOfTopics)
            .timestamp(timestamp)
            .topics(topics)
            .words(words)
            .documents(documents)
            .build();
    }

    public void save(DtoLda dtoLda) {
        // TODO: Maybe a composite key will remove the need for storing corpusName and numberOfTopics in each document for retrieval
        final String corpusName = dtoLda.getCorpusName();
        final int numberOfTopics = dtoLda.getNumberOfTopics();
        final String timestamp = dtoLda.getTimestamp();
        LdaParameterCombination ldaParameterCombination = LdaParameterCombination.builder()
            .corpusName(corpusName)
            .numberOfTopics(numberOfTopics)
            .timestamp(timestamp)
            .build();
        ldaParameterCombinationRepository.save(ldaParameterCombination);
        List<DtoSection> sections = groupBySectionAndVolume(dtoLda.getDocuments());
        for (DtoSection section : sections) {
            section.setCorpusName(corpusName);
            section.setNumberOfTopics(numberOfTopics);
            section.setTimestamp(timestamp);
            sectionRepository.save(section);
        }
        for (Topic topic : dtoLda.getTopics()) {
            topic.setCorpusName(corpusName);
            topic.setNumberOfTopics(numberOfTopics);
            topic.setTimestamp(timestamp);
            topicRepository.save(topic);
        }
        for (ContentWord word : dtoLda.getWords()) {
            word.setCorpusName(corpusName);
            word.setNumberOfTopics(numberOfTopics);
            word.setTimestamp(timestamp);
            contentWordRepository.save(word);
        }
        for (DtoDocument document : dtoLda.getDocuments()) {
            document.setCorpusName(corpusName);
            document.setNumberOfTopics(numberOfTopics);
            document.setTimestamp(timestamp);
            documentRepository.save(document);
        }
    }

    public void save(Lda lda) {
        save(lda.getDtoLda());
    }

    public void clearBy(String corpusName, int numberOfTopics, String timestamp) {
        topicRepository.deleteByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
        contentWordRepository.deleteByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
        documentRepository.deleteByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
        ldaParameterCombinationRepository.deleteByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public void clearAll() {
        topicRepository.deleteAll();
        contentWordRepository.deleteAll();
        documentRepository.deleteAll();
        ldaParameterCombinationRepository.deleteAll();
    }

    public List<LdaParameterCombination> fetchAll() {
        return ldaParameterCombinationRepository.findAll();
    }

    public List<DtoSection> fetchAllByAndGroupBySections(String corpusName, int numberOfTopics, String timestamp) {
        return sectionRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public void create(LdaParameterCombination ldaParameterCombination) {
        String corpusName = ldaParameterCombination.getCorpusName();
        int numberOfTopics = ldaParameterCombination.getNumberOfTopics();
        Corpus corpus = corpusService.fetchBy(corpusName);
        Lda lda = Lda.fromCorpus(corpus, numberOfTopics);
        save(lda);
    }

    List<DtoSection> groupBySectionAndVolume(List<DtoDocument> documents) {
        HashMap<String, List<DtoDocument>> volumes = new HashMap<>(documents.stream()
            .collect(groupingBy(DtoDocument::getVolume)));
        HashMap<String, List<DtoVolume>> sections = new HashMap<>(volumes.entrySet().stream()
            .map(entry -> DtoVolume.builder()
                .title(entry.getKey())
                .documentTitles(entry.getValue().stream()
                    .map(DtoDocument::getTitle)
                    .collect(Collectors.toList()))
                .build())
            .collect(groupingBy(volume -> volume.getSection())));
        List<DtoSection> sectionsList = sections.entrySet().stream()
            .map(entry -> {
                List<DtoVolume> volumesList = entry.getValue();
                Collections.sort(volumesList);
                return DtoSection.builder()
                    .title(entry.getKey())
                    .volumes(volumesList)
                    .build();
            })
            .collect(Collectors.toList());
        sort(sectionsList);
        return sectionsList;
    }
}