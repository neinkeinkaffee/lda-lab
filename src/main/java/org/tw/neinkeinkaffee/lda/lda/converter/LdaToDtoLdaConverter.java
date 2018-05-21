package org.tw.neinkeinkaffee.lda.lda.converter;

import org.tw.neinkeinkaffee.lda.dto.token.Token;
import org.tw.neinkeinkaffee.lda.dto.topic.Topic;
import org.tw.neinkeinkaffee.lda.dto.document.DtoDocument;
import org.tw.neinkeinkaffee.lda.dto.lda.DtoLda;
import org.tw.neinkeinkaffee.lda.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.dto.probability.WordProbability;
import org.tw.neinkeinkaffee.lda.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.lda.Lda;
import org.tw.neinkeinkaffee.lda.lda.LdaDocument;
import org.tw.neinkeinkaffee.lda.lda.PairCounter;
import org.tw.neinkeinkaffee.lda.lda.SimpleCounter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class LdaToDtoLdaConverter implements Converter<Lda, DtoLda> {
    private static final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("MMM-d-yyyy-hh:mm-a")
        .withZone(ZoneId.systemDefault());

    @Override
    public DtoLda convert(Lda lda) {
        PairCounter<Integer, String> topicWordCounts = lda.getTopicWordCounts();
        PairCounter<Integer, String> topicMultiWordCounts = lda.getTopicMultiWordCounts();
        PairCounter<Integer, String> topicDocumentCounts = lda.getTopicDocumentCounts();
        PairCounter<String, Integer> wordTopicCounts = lda.getWordTopicCounts();
        PairCounter<String, Integer> documentTopicCounts = lda.getDocumentTopicCounts();
        HashMap<String, LdaDocument> ldaDocuments = lda.getDocuments();

        HashMap<String, DtoDocument> documentMap = new HashMap<>(documentTopicCounts.stream()
            .map(entry -> {
                String title = entry.getKey();
                LdaDocument ldaDocument = ldaDocuments.get(title);
                return DtoDocument.builder()
                    .title(title)
                    .author(ldaDocument.getAuthor())
                    .volume(ldaDocument.getVolume())
                    .tokens(ldaDocument.getTokens().stream()
                        .map(token -> {
                            if (token.isStopword())
                                return Token.builder()
                                    .stopToken(true)
                                    .lemma(token.getLemma())
                                    .build();
                            else
                                return Token.builder()
                                    .stopToken(false)
                                    .lemma(token.getLemma())
                                    .topicId(token.getTopic())
                                    .build();
                        })
                        .collect(Collectors.toList()))
                    .build();
            })
            .collect((Collectors.toMap(document -> document.getTitle(), document -> document))));

        List<Topic> topics = topicWordCounts.stream()
            .map(counterEntry -> {
                int topicId = counterEntry.getKey();

                SimpleCounter<String> wordCounts = counterEntry.getValue();
                int sumOfWordCounts = wordCounts.stream()
                    .mapToInt(w -> w.getValue())
                    .sum();
                List<WordProbability> wordProbabilities = wordCounts.stream()
                    .map(wordCount -> WordProbability.builder()
                        .lemma(wordCount.getKey())
                        .probability((double) wordCount.getValue() / sumOfWordCounts)
                        .build())
                    .collect(Collectors.toList());
                Collections.sort(wordProbabilities, Collections.reverseOrder());

                SimpleCounter<String> multiWordCounts = topicMultiWordCounts.getCount(topicId);
                int sumOfMultiWordCounts = multiWordCounts.stream()
                    .mapToInt(w -> w.getValue())
                    .sum();
                // TODO: multiWord isn't strictly a ContentWord and the statistics aren't strictly probabilities
                List<WordProbability> multiWordProbabilities = multiWordCounts.stream()
                    .map(multiWordCount -> WordProbability.builder()
                        .lemma(multiWordCount.getKey())
                        .probability((double) multiWordCount.getValue() / sumOfMultiWordCounts)
                        .build())
                    .collect(Collectors.toList());
                Collections.sort(multiWordProbabilities, Collections.reverseOrder());

                SimpleCounter<String> documentCounts = topicDocumentCounts.getCount(topicId);
                List<DocumentProbability> documentProbabilities = documentCounts.stream()
                    .map(documentCount -> {
                        String title = documentCount.getKey();
                        int sumOfTopicCounts = documentTopicCounts.getCount(title).stream()
                            .mapToInt(d -> d.getValue())
                            .sum();
                        return DocumentProbability.builder()
                            .document(DtoDocument.builder()
                                .title(title)
                                .author(documentMap.get(title).getAuthor())
                                .volume(documentMap.get(title).getVolume())
                                .build())
                            .probability((double) documentCount.getValue() / sumOfTopicCounts)
                            .build();
                    })
                    .collect(Collectors.toList());
                Collections.sort(documentProbabilities, Collections.reverseOrder());

                return Topic.builder()
                    .topicId(topicId)
                    .wordProbabilities(wordProbabilities)
                    .multiWordProbabilities(multiWordProbabilities)
                    .documentProbabilities(documentProbabilities)
                    .build();
            })
            .collect(Collectors.toList());

        List<ContentWord> words = wordTopicCounts.stream()
            .map(counterEntry -> {
                String lemma = counterEntry.getKey();

                SimpleCounter<Integer> topicCounts = counterEntry.getValue();
                int sumOfTopicCounts = topicCounts.stream()
                    .mapToInt(topic -> topic.getValue())
                    .sum();
                List<TopicProbability> topicProbabilities = topicCounts.stream()
                    .map(topicCount -> TopicProbability.builder()
                        .topic(Topic.builder()
                            .topicId(topicCount.getKey())
                            .topWords(topics.get(topicCount.getKey()).getTopWordsSignature())
                            .build())
                        .probability((double) topicCount.getValue() / sumOfTopicCounts)
                        .build())
                    .collect(Collectors.toList());
                Collections.sort(topicProbabilities, Collections.reverseOrder());

                return ContentWord.builder()
                    .lemma(lemma)
                    .topicProbabilities(topicProbabilities)
                    .build();
            })
            .collect(Collectors.toList());

        List<DtoDocument> documents = documentTopicCounts.stream()
            .map(counterEntry -> {
                String title = counterEntry.getKey();

                SimpleCounter<Integer> topicCounts = counterEntry.getValue();
                int sumOfTopicCounts = topicCounts.stream()
                    .mapToInt(topic -> topic.getValue())
                    .sum();
                List<TopicProbability> topicProbabilities = topicCounts.stream()
                    .map(topicCount -> TopicProbability.builder()
                        .topic(Topic.builder()
                            .topicId(topicCount.getKey())
                            .topWords(topics.get(topicCount.getKey()).getTopWordsSignature())
                            .build())
                        .probability((double) topicCount.getValue() / sumOfTopicCounts)
                        .build())
                    .collect(Collectors.toList());
                Collections.sort(topicProbabilities, Collections.reverseOrder());

                DtoDocument document = documentMap.get(title);
                document.setTopicProbabilities(topicProbabilities);
                return document;
            })
            .collect(Collectors.toList());

        return DtoLda.builder()
            .corpusName(lda.getCorpusName())
            .numberOfTopics(lda.getNumberOfTopics())
            .timestamp(formatter.format(Instant.now()))
            .topics(topics)
            .words(words)
            .documents(documents)
            .build();
    }
}
