package org.tw.neinkeinkaffee.lda.helper;

import org.springframework.stereotype.Component;
import org.tw.neinkeinkaffee.lda.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class SyntheticDataProvider {
    public Lda getByCorpusIdAndNumberOfTopics(int corpusId, int numberOfTopics) {
        // CorpusID and numberOfTopics get ignored by the SyntheticDataProvider.
        // TODO: CorpusID and numberOfTopics should be used to retrieve the lda model from a proper repository with a composite key, as described in http://software-sympathy.blogspot.de/2017/01/spring-data-with-mongodb-and-composite.html

        Topic topic0 = Topic.builder()
            .id(0)
            .build();
        Topic topic1 = Topic.builder()
            .id(1)
            .build();
        Topic topic2 = Topic.builder()
            .id(2)
            .build();

        List<Topic> topics = Arrays.asList(topic0, topic1, topic2);

        // Word-Topic Probabilities
        // NOTE: In real life, there would be on List of topicProbabilities for each Word, but here we'll just assign the same dummy topicProbabilities to each word
        List<TopicProbability> dummyTopicProbabilities = Arrays.asList(
            TopicProbability.builder()
                .topic(topic0)
                .probability(.45)
                .build(),
            TopicProbability.builder()
                .topic(topic1)
                .probability(.30)
                .build(),
            TopicProbability.builder()
                .topic(topic2)
                .probability(.25)
                .build());

        Word banana = Word.builder()
            .lemma("banana")
            .build();
        Word kiwi = Word.builder()
            .lemma("kiwi")
            .build();
        Word plum = Word.builder()
            .lemma("plum")
            .build();
        Word apple = Word.builder()
            .lemma("apple")
            .build();
        Word fruit = Word.builder()
            .lemma("fruit")
            .build();

        Word mix = Word.builder()
            .lemma("mix")
            .build();
        Word mash = Word.builder()
            .lemma("mash")
            .build();
        Word stash = Word.builder()
            .lemma("stash")
            .build();
        Word boil = Word.builder()
            .lemma("boil")
            .build();
        Word fry = Word.builder()
            .lemma("fry")
            .build();

        Word healthy = Word.builder()
            .lemma("healthy")
            .build();
        Word tasty = Word.builder()
            .lemma("tasty")
            .build();
        Word greasy = Word.builder()
            .lemma("greasy")
            .build();
        Word oily = Word.builder()
            .lemma("oily")
            .build();
        Word spicy = Word.builder()
            .lemma("spicy")
            .build();

        HashMap<String, Word> words = new HashMap<>();
        List<Word> wordList = Arrays.asList(banana, kiwi, plum, apple, fruit, mix, mash, stash, boil, fry, healthy, tasty, greasy, oily, spicy);
        for (Word word : wordList) {
            word.setTopicProbabilities(dummyTopicProbabilities);
            words.put(word.getLemma(), word);
        }

        // Topic-Word Probabilities
        List<WordProbability> wordProbabilities0 = Arrays.asList(
            WordProbability.builder()
                .word(banana)
                .probability(.35)
                .build(),
            WordProbability.builder()
                .word(kiwi)
                .probability(.30)
                .build(),
            WordProbability.builder()
                .word(plum)
                .probability(.25)
                .build(),
            WordProbability.builder()
                .word(apple)
                .probability(.20)
                .build(),
            WordProbability.builder()
                .word(fruit)
                .probability(.15)
                .build());

        List<WordProbability> wordProbabilities1 = Arrays.asList(
            WordProbability.builder()
                .word(mix)
                .probability(.30)
                .build(),
            WordProbability.builder()
                .word(mash)
                .probability(.28)
                .build(),
            WordProbability.builder()
                .word(stash)
                .probability(.25)
                .build(),
            WordProbability.builder()
                .word(boil)
                .probability(.15)
                .build(),
            WordProbability.builder()
                .word(fry)
                .probability(.2)
                .build());

        List<WordProbability> wordProbabilities2 = Arrays.asList(
            WordProbability.builder()
                .word(healthy)
                .probability(.37)
                .build(),
            WordProbability.builder()
                .word(tasty)
                .probability(.28)
                .build(),
            WordProbability.builder()
                .word(greasy)
                .probability(.15)
                .build(),
            WordProbability.builder()
                .word(oily)
                .probability(.14)
                .build(),
            WordProbability.builder()
                .word(spicy)
                .probability(.6)
                .build());

        topic0.setWordProbabilities(wordProbabilities0);
        topic1.setWordProbabilities(wordProbabilities1);
        topic2.setWordProbabilities(wordProbabilities2);

        HashMap<String, Document> documents = new HashMap<>();
        List<Document> documentList = Arrays.asList(
            Document.builder()
                .name("banana_cake")
                .tokens(Arrays.asList(
                    Token.builder()
                        .word(banana)
                        .topic(topic0)
                        .build(),
                    Token.builder()
                        .word(banana)
                        .topic(topic0)
                        .build(),
                    Token.builder()
                        .word(fry)
                        .topic(topic1)
                        .build(),
                    Token.builder()
                        .word(oily)
                        .topic(topic2)
                        .build(),
                    Token.builder()
                        .word(tasty)
                        .topic(topic2)
                        .build()))
                .build(),
            Document.builder()
                .name("church_song")
                .tokens(Arrays.asList(
                    Token.builder()
                        .word(fruit)
                        .topic(topic0)
                        .build(),
                    Token.builder()
                        .word(fry)
                        .topic(topic1)
                        .build(),
                    Token.builder()
                        .word(stash)
                        .topic(topic1)
                        .build(),
                    Token.builder()
                        .word(boil)
                        .topic(topic1)
                        .build(),
                    Token.builder()
                        .word(oily)
                        .topic(topic2)
                        .build()))
                .build(),
            Document.builder()
                .name("wok_manual")
                .tokens(Arrays.asList(
                    Token.builder()
                        .word(plum)
                        .topic(topic0)
                        .build(),
                    Token.builder()
                        .word(fry)
                        .topic(topic1)
                        .build(),
                    Token.builder()
                        .word(boil)
                        .topic(topic1)
                        .build(),
                    Token.builder()
                        .word(greasy)
                        .topic(topic2)
                        .build(),
                    Token.builder()
                        .word(tasty)
                        .topic(topic2)
                        .build()))
                .build());
        for (Document document : documentList) {
            document.setTopicProbabilities(dummyTopicProbabilities);
            documents.put(document.getName(), document);
        }

        Lda lda = Lda.builder()
            .documents(documents)
            .topics(topics)
            .words(words)
            .build();

        return lda;
    }
}
