package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.converter.CorpusToLdaDocumentConverter;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Lda {
    private static CorpusToLdaDocumentConverter documentConverter = new CorpusToLdaDocumentConverter();
    private static Random random = new Random();
    private static final int NUMBER_OF_ITERATIONS = 50;
    private static final double DOCUMENT_TOPIC_SMOOTHING = 0.1;
    private static final double TOPIC_WORD_SMOOTHING = 0.1;
    private HashMap<Integer, Double> topicWeights;
    private SimpleCounter<Integer> topicCounts;
    private PairCounter<Integer, String> topicWordCounts;
    private PairCounter<Integer, LdaDocument> topicDocumentCounts;
    private PairCounter<String, Integer> wordTopicCounts;
    private PairCounter<LdaDocument, Integer> documentTopicCounts;
    private int sizeOfVocabulary;

    @Getter
    private int numberOfTopics;
    @Getter
    private List<LdaDocument> documents;

    public Lda(Corpus corpus, int numberOfTopics) {
        this.numberOfTopics = numberOfTopics;
        initializeCountsAndWeights();
        convertDocuments(corpus);
        assignRandomTopics();
        iterate();
        // TODO: convert to dto
        // TODO: save to repo
    }

    private void iterate() {
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            sweep();
        }
    }

    private void initializeCountsAndWeights() {
        topicCounts = new SimpleCounter<Integer>();
        topicWordCounts = new PairCounter<Integer, String>();
        topicDocumentCounts = new PairCounter<Integer, LdaDocument>();
        wordTopicCounts = new PairCounter<String, Integer>();
        documentTopicCounts = new PairCounter<LdaDocument, Integer>();
        topicWeights = new HashMap<>();
        sizeOfVocabulary = wordTopicCounts.size();
    }

    private void sweep() {
        for (LdaDocument document : documents) {
            for (LdaToken token : document.getTokens()) {
                if (!token.isStopword()) {
                    decreaseCounts(token, document);
                    updateWeights(token, document);
                    token.setTopic(sampleDiscrete());
                    increaseCounts(token, document);
                }
            }
        }
    }

    private int sampleDiscrete() {
        double sumOfTopicWeights = topicWeights.values().stream()
            .mapToDouble(w -> w.doubleValue())
            .sum();
        double sample = random.nextDouble() * sumOfTopicWeights;
        int i = 0;
        sample -= topicWeights.get(i);
        while (sample > 0) {
            i++;
            sample -= topicWeights.get(i);
        }
        return i;
    }

    private void updateWeights(LdaToken token, LdaDocument document) {
        for (int topic = 0; topic < numberOfTopics; topic++) {
            String lemma = token.getLemma();
            double updatedWeight =
                (DOCUMENT_TOPIC_SMOOTHING + documentTopicCounts.get(document, topic)) * (TOPIC_WORD_SMOOTHING + wordTopicCounts.get(lemma, topic)) / (sizeOfVocabulary * TOPIC_WORD_SMOOTHING + topicCounts.get(topic));
            topicWeights.put(topic, updatedWeight);
        }
    }

    private void increaseCounts(LdaToken token, LdaDocument document) {
        int topic = token.getTopic();
        String lemma = token.getLemma();
        topicCounts.increaseByOne(topic);
        topicWordCounts.increaseByOne(topic, lemma);
        topicDocumentCounts.increaseByOne(topic, document);
        wordTopicCounts.increaseByOne(lemma, topic);
        documentTopicCounts.increaseByOne(document, topic);
    }

    private void decreaseCounts(LdaToken token, LdaDocument document) {
        int topic = token.getTopic();
        String lemma = token.getLemma();
        topicCounts.decreaseByOne(topic);
        topicWordCounts.decreaseByOne(topic, lemma);
        topicDocumentCounts.decreaseByOne(topic, document);
        wordTopicCounts.decreaseByOne(lemma, topic);
        documentTopicCounts.decreaseByOne(document, topic);
    }

    private void assignRandomTopics() {
        for (LdaDocument document : documents) {
            for (LdaToken token : document.getTokens()) {
                if (!token.isStopword()) {
                    token.setTopic(random.nextInt(numberOfTopics));
                    increaseCounts(token, document);
                }
            }
        }
    }

    private void convertDocuments(Corpus corpus) {
        documents = corpus.getDocuments().stream()
            .map(document -> documentConverter.convert(document))
            .collect(Collectors.toList());
    }
}
