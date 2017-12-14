package org.tw.neinkeinkaffee.lda.model.converter;

import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.model.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.WordProbability;
import org.tw.neinkeinkaffee.lda.model.dto.token.ContentToken;
import org.tw.neinkeinkaffee.lda.model.dto.token.StopToken;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.dto.word.StopWord;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.model.lda.LdaDocument;
import org.tw.neinkeinkaffee.lda.model.lda.PairCounter;
import org.tw.neinkeinkaffee.lda.model.lda.SimpleCounter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LdaToDtoLdaConverter implements Converter<Lda, DtoLda> {
    @Override
    public DtoLda convert(Lda lda) {
        PairCounter<Integer, String> topicWordCounts = lda.getTopicWordCounts();
        PairCounter<Integer, String> topicDocumentCounts = lda.getTopicDocumentCounts();
        PairCounter<String, Integer> wordTopicCounts = lda.getWordTopicCounts();
        PairCounter<String, Integer> documentTopicCounts = lda.getDocumentTopicCounts();

        HashMap<Integer, Topic> topicMap = new HashMap<>(topicWordCounts.stream()
            .map(entry -> Topic.builder()
                .id(entry.getKey())
                .build())
            .collect(Collectors.toMap(topic -> topic.getId(), topic -> topic)));

        HashMap<String, ContentWord> wordMap = new HashMap<>(wordTopicCounts.stream()
            .map(entry -> ContentWord.builder()
                .lemma(entry.getKey())
                .build())
            .collect(Collectors.toMap(word -> word.getLemma(), word -> word)));

        // TODO: It would be nice to extract the nested document and token conversions, but how to achieve the linking of the references to the ContentWords from the wordMap into the ContentTokens in that case?
        HashMap<String, DtoDocument> documentMap = new HashMap<>(documentTopicCounts.stream()
            .map(entry -> DtoDocument.builder()
                .title(entry.getKey())
                .tokens(lda.getDocuments().get(entry.getKey()).getTokens().stream()
                    .map(token -> {
                        if (token.isStopword())
                            return StopToken.builder()
                                .word(StopWord.builder().lemma(token.getLemma()).build())
                                .build();
                        else
                            return ContentToken.builder()
                                .word(wordMap.get(token.getLemma()))
                                .build();
                    })
                    .collect(Collectors.toList()))
                .build())
            .collect((Collectors.toMap(document -> document.getTitle(), document -> document))));

        List<Topic> topics = topicMap.entrySet().stream()
            .map(entry -> {
                Topic topic = entry.getValue();
                int topicId = topic.getId();

                SimpleCounter<String> wordCounts = topicWordCounts.getCount(topicId);
                int sumOfWordCounts = wordCounts.stream()
                    .mapToInt(w -> w.getValue())
                    .sum();
                List<WordProbability> wordProbabilities = wordCounts.stream()
                    .map(wordCount -> WordProbability.builder()
                        .word(wordMap.get(wordCount.getKey()))
                        .probability((double) wordCount.getValue() / sumOfWordCounts)
                        .build())
                    .collect(Collectors.toList());

                SimpleCounter<String> documentCounts = topicDocumentCounts.getCount(topicId);
                int sumOfDocumentCounts = documentCounts.stream()
                    .mapToInt(d -> d.getValue())
                    .sum();
                List<DocumentProbability> documentProbabilities = documentCounts.stream()
                    .map(documentCount -> DocumentProbability.builder()
                        .document(documentMap.get(documentCount.getKey()))
                        .probability((double) documentCount.getValue() / sumOfDocumentCounts)
                        .build())
                    .collect(Collectors.toList());

                topic.setWordProbabilities(wordProbabilities);
                topic.setDocumentProbabilities(documentProbabilities);
                return topic;
            })
            .collect(Collectors.toList());

        HashMap<String, ContentWord> words = new HashMap<String, ContentWord>(wordMap.entrySet().stream()
            .map(entry -> {
                ContentWord word = entry.getValue();

                SimpleCounter<Integer> topicCounts = wordTopicCounts.getCount(entry.getKey());
                int sumOfTopicCounts = topicCounts.stream()
                    .mapToInt(topic -> topic.getValue())
                    .sum();
                List<TopicProbability> topicProbabilities = topicCounts.stream()
                    .map(topicCount -> TopicProbability.builder()
                        .topic(topicMap.get(topicCount.getKey()))
                        .probability((double) topicCount.getValue() / sumOfTopicCounts)
                        .build())
                    .collect(Collectors.toList());

                word.setTopicProbabilities(topicProbabilities);
                return word;
            })
            .collect(Collectors.toMap(word -> word.getLemma(), word -> word)));

        HashMap<String, DtoDocument> documents = new HashMap<>(documentMap.entrySet().stream()
            .map(entry -> {
                DtoDocument document = entry.getValue();

                SimpleCounter<Integer> topicCounts = documentTopicCounts.getCount(entry.getKey());
                int sumOfTopicCounts = topicCounts.stream()
                    .mapToInt(topic -> topic.getValue())
                    .sum();
                List<TopicProbability> topicProbabilities = topicCounts.stream()
                    .map(topicCount -> TopicProbability.builder()
                    .topic(topicMap.get(topicCount.getKey()))
                    .probability((double) topicCount.getValue() / sumOfTopicCounts)
                    .build())
                    .collect(Collectors.toList());

                document.setTopicProbabilities(topicProbabilities);
                return document;
            })
            .collect(Collectors.toMap(document -> document.getTitle(), document -> document)));

        return DtoLda.builder()
            .corpusName(lda.getCorpusName())
            .numberOfTopics(lda.getNumberOfTopics())
            .topics(topics)
            .words(words)
            .documents(documents)
            .build();
    }
}
