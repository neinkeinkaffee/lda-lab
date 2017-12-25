package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.LdaParameterCombination;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.repository.ContentWordRepository;
import org.tw.neinkeinkaffee.lda.repository.DocumentRepository;
import org.tw.neinkeinkaffee.lda.repository.LdaParameterCombinationRepository;
import org.tw.neinkeinkaffee.lda.repository.TopicRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class LdaService {
    // TODO: Should encapsulate the repositories for the Corpus and for the Lda in super repositories that are injected into the CorpusService and LdaService (LdaService can also get a corpusRepository instead of the corpusService)
    private TopicRepository topicRepository;
    private ContentWordRepository contentWordRepository;
    private DocumentRepository documentRepository;
    private CorpusService corpusService;
    private LdaParameterCombinationRepository ldaParameterCombinationRepository;

    @Autowired
    public LdaService(final TopicRepository topicRepository,
                      final ContentWordRepository contentWordRepository,
                      final DocumentRepository documentRepository,
                      final CorpusService corpusService,
                      final LdaParameterCombinationRepository ldaParameterCombinationRepository) {
        this.topicRepository = topicRepository;
        this.contentWordRepository = contentWordRepository;
        this.documentRepository = documentRepository;
        this.corpusService = corpusService;
        this.ldaParameterCombinationRepository = ldaParameterCombinationRepository;
    }

    public DtoLda fetchBy(String corpusName, int numberOfTopics, Instant timestamp) {
//        if (corpusName.equals("toyCorpus")) return toyDataProvider.produceToyLda("toyCorpus");
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
        final Instant timestamp = dtoLda.getTimestamp();
        LdaParameterCombination ldaParameterCombination = LdaParameterCombination.builder()
            .corpusName(corpusName)
            .numberOfTopics(numberOfTopics)
            .timestamp(timestamp)
            .build();
        ldaParameterCombinationRepository.save(ldaParameterCombination);
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

    public void clearBy(String corpusName, int numberOfTopics, Instant timestamp) {
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

    public void create(LdaParameterCombination ldaParameterCombination) {
        String corpusName = ldaParameterCombination.getCorpusName();
        int numberOfTopics = ldaParameterCombination.getNumberOfTopics();
        Corpus corpus = corpusService.fetchBy(corpusName);
        Lda lda = Lda.fromCorpus(corpus, numberOfTopics);
        save(lda);
    }
}