package org.tw.neinkeinkaffee.lda.lda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tw.neinkeinkaffee.lda.lda.converter.CorpusDocumentToLdaDocumentConverter;
import org.tw.neinkeinkaffee.lda.lda.converter.LdaToDtoLdaConverter;
import org.tw.neinkeinkaffee.lda.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.dto.lda.DtoLda;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public class Lda {

    public static final int MULTI_WORDS_MIN_COUNT = 2;
    private static final int NUMBER_OF_ITERATIONS = 50;
    private static final double DOCUMENT_TOPIC_SMOOTHING = 0.1;
    private static final double TOPIC_WORD_SMOOTHING = 0.1;

    private static CorpusDocumentToLdaDocumentConverter documentConverter = new CorpusDocumentToLdaDocumentConverter();
    private static Random random = new Random();

    @Getter
    private String corpusName;
    @Getter
    private int numberOfTopics;

    private HashMap<Integer, Double> topicWeights;
    private SimpleCounter<Integer> topicCounts;
    @Getter
    private PairCounter<Integer, String> topicWordCounts;
    @Getter
    private PairCounter<Integer, String> topicMultiWordCounts;
    @Getter
    private PairCounter<Integer, String> topicDocumentCounts;
    @Getter
    private PairCounter<String, Integer> wordTopicCounts;
    @Getter
    private PairCounter<String, Integer> documentTopicCounts;
    private int sizeOfVocabulary;
    @Getter
    private HashMap<String, LdaDocument> documents;
    @Getter(lazy = true)
    private final DtoLda dtoLda = convertToDtoLda();

    public static Lda fromCorpus(Corpus corpus, int numberOfTopics) {
        Lda lda = new Lda();
        lda.numberOfTopics = numberOfTopics;
        lda.corpusName = corpus.getName();
        lda.initializeCountsAndWeights();
        lda.convertDocuments(corpus);
        lda.assignRandomTopics();
        lda.iterate();
        lda.countMultiWords(MULTI_WORDS_MIN_COUNT);
        return lda;
    }

    private void iterate() {
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            sweep();
        }
    }

    private void initializeCountsAndWeights() {
        topicCounts = new SimpleCounter<Integer>();
        topicWordCounts = new PairCounter<Integer, String>();
        topicMultiWordCounts = new PairCounter<Integer, String>();
        topicDocumentCounts = new PairCounter<Integer, String>();
        wordTopicCounts = new PairCounter<String, Integer>();
        documentTopicCounts = new PairCounter<String, Integer>();
        topicWeights = new HashMap<>();
        sizeOfVocabulary = wordTopicCounts.size();
    }

    private void sweep() {
        for (LdaDocument document : documents.values()) {
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
                (DOCUMENT_TOPIC_SMOOTHING + documentTopicCounts.getCount(document.getTitle(), topic)) * (TOPIC_WORD_SMOOTHING + wordTopicCounts.getCount(lemma, topic)) / (sizeOfVocabulary * TOPIC_WORD_SMOOTHING + topicCounts.getCount(topic));
            topicWeights.put(topic, updatedWeight);
        }
    }

    private void increaseCounts(LdaToken token, LdaDocument document) {
        int topic = token.getTopic();
        String lemma = token.getLemma();
        topicCounts.increaseByOne(topic);
        topicWordCounts.increaseByOne(topic, lemma);
        topicDocumentCounts.increaseByOne(topic, document.getTitle());
        wordTopicCounts.increaseByOne(lemma, topic);
        documentTopicCounts.increaseByOne(document.getTitle(), topic);
    }

    private void decreaseCounts(LdaToken token, LdaDocument document) {
        int topic = token.getTopic();
        String lemma = token.getLemma();
        topicCounts.decreaseByOne(topic);
        topicWordCounts.decreaseByOne(topic, lemma);
        topicDocumentCounts.decreaseByOne(topic, document.getTitle());
        wordTopicCounts.decreaseByOne(lemma, topic);
        documentTopicCounts.decreaseByOne(document.getTitle(), topic);
    }

    private void assignRandomTopics() {
        for (LdaDocument document : documents.values()) {
            for (LdaToken token : document.getTokens()) {
                if (!token.isStopword()) {
                    token.setTopic(random.nextInt(numberOfTopics));
                    increaseCounts(token, document);
                }
            }
        }
    }

    private void convertDocuments(Corpus corpus) {
        documents = new HashMap<>();
        for (CorpusDocument corpusDocument : corpus.getDocuments()) {
            LdaDocument document = documentConverter.convert(corpusDocument);
            String title = document.getTitle();
            if (documents.containsKey(title)) {
                document.setTitle(title + "_DUPLICATE");
            }
            documents.put(document.getTitle(), document);
        }
    }

    private DtoLda convertToDtoLda() {
        LdaToDtoLdaConverter ldaConverter = new LdaToDtoLdaConverter();
        return ldaConverter.convert(this);
    }

    // TODO: In order to be testable, this method would have to be package-private and get the Lda object passed in as a parameter
    private void countMultiWords(int minCount) {
        for (LdaDocument document : this.documents.values()) {
            List<LdaToken> tokens = document.getTokens();
            countMultiWords(tokens, topicMultiWordCounts, minCount);
        }
    }

    void countMultiWords(List<LdaToken> tokens, PairCounter<Integer, String> topicMultiWordCounts, int minCount) {
        for (int i = 0; i < tokens.size() - 1; i++) {
            int currentIndex = i;
            LdaToken currentToken = tokens.get(currentIndex);
            if (!currentToken.isStopword()) {
                int nextIndex = i + 1;
                LdaToken nextToken = tokens.get(nextIndex);
                if (!nextToken.isStopword() && currentToken.getTopic() == nextToken.getTopic()) {
                    i++;
                    String multiWord = currentToken.getLemma() + nextToken.getLemma();
                    topicMultiWordCounts.increaseByOne(currentToken.getTopic(), multiWord);
                    while (i < tokens.size() - 1
                        && !tokens.get(i + 1).isStopword()
                        && tokens.get(i + 1).getTopic() == currentToken.getTopic()) {
                        i++;
                        multiWord += tokens.get(i).getLemma();
                        topicMultiWordCounts.increaseByOne(currentToken.getTopic(), multiWord);
                    }
                }
            }
        }
        topicMultiWordCounts.filterCountsLessThan(minCount);
    }
}
