package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.helper.ToyDataProvider;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.LdaParameterCombination;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.model.lda.LdaDocument;
import org.tw.neinkeinkaffee.lda.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LdaService {
    private TopicRepository topicRepository;
    private ContentWordRepository contentWordRepository;
    private DocumentRepository documentRepository;
    private LdaParameterCombinationRepository ldaParameterCombinationRepository;
    private ToyDataProvider toyDataProvider;

    @Autowired
    public LdaService(final TopicRepository topicRepository,
                      final ContentWordRepository contentWordRepository,
                      final DocumentRepository documentRepository,
                      final LdaParameterCombinationRepository ldaParameterCombinationRepository,
                      final ToyDataProvider provider) {
        this.topicRepository = topicRepository;
        this.contentWordRepository = contentWordRepository;
        this.documentRepository = documentRepository;
        this.ldaParameterCombinationRepository = ldaParameterCombinationRepository;
        this.toyDataProvider = provider;
    }

    public DtoLda fetchBy(String corpusName, int numberOfTopics) {
//        if (corpusName.equals("toyCorpus")) return toyDataProvider.produceToyLda("toyCorpus");
        // TODO: check in parametercombinationsrepository whether model exists
        List<Topic> topics = topicRepository.findAllByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        List<ContentWord> words = contentWordRepository.findAllByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        List<DtoDocument> documents = documentRepository.findAllByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        return DtoLda.builder()
            .corpusName(corpusName)
            .numberOfTopics(numberOfTopics)
            .topics(topics)
            .words(words)
            .documents(documents)
            .build();
    }

    public void save(DtoLda dtoLda) {
        // TODO: Maybe a composite key will remove the need for storing corpusName and numberOfTopics in each document for retrieval
        final String corpusName = dtoLda.getCorpusName();
        final int numberOfTopics = dtoLda.getNumberOfTopics();
        LdaParameterCombination ldaParameterCombination = LdaParameterCombination.builder()
            .corpusName(corpusName)
            .numberOfTopics(numberOfTopics)
            .build();
        ldaParameterCombinationRepository.save(ldaParameterCombination);
        for (Topic topic : dtoLda.getTopics()) {
            topic.setCorpusName(corpusName);
            topic.setNumberOfTopics(numberOfTopics);
            topicRepository.save(topic);
        }
        for (ContentWord word : dtoLda.getWords()) {
            word.setCorpusName(corpusName);
            word.setNumberOfTopics(numberOfTopics);
            contentWordRepository.save(word);
        }
        for (DtoDocument document : dtoLda.getDocuments()) {
            document.setCorpusName(corpusName);
            document.setNumberOfTopics(numberOfTopics);
            documentRepository.save(document);
        }
    }

    public void save(Lda lda) {
        save(lda.getDtoLda());
    }

    public void clearAll() {
        topicRepository.deleteAll();
        contentWordRepository.deleteAll();
        documentRepository.deleteAll();
        ldaParameterCombinationRepository.deleteAll();
    }

    public void clearBy(String corpusName, int numberOfTopics) {
        topicRepository.deleteByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        contentWordRepository.deleteByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        documentRepository.deleteByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
        ldaParameterCombinationRepository.deleteByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
    }
}