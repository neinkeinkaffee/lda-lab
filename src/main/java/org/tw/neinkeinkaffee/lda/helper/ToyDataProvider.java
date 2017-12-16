package org.tw.neinkeinkaffee.lda.helper;

import org.springframework.stereotype.Component;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.WordProbability;
import org.tw.neinkeinkaffee.lda.model.dto.token.ContentToken;
import org.tw.neinkeinkaffee.lda.model.dto.token.StopToken;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.dto.word.StopWord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class ToyDataProvider {
    public static DtoLda produceToyLda(String corpusName) {
        // CorpusID and numberOfTopics getCount ignored by the SyntheticDataProvider.
        // TODO: CorpusID and numberOfTopics should be used to retrieve the dto model from a proper repository with a composite key, as described in http://software-sympathy.blogspot.de/2017/01/spring-data-with-mongodb-and-composite.html

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

        // ContentWord-Topic Probabilities
        // NOTE: In real life, there would be on List of topicProbabilities for each ContentWord, but here we'll just assign the same dummy topicProbabilities to each word
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

        ContentWord banana = ContentWord.builder()
            .lemma("banana")
            .build();
        ContentWord kiwi = ContentWord.builder()
            .lemma("kiwi")
            .build();
        ContentWord plum = ContentWord.builder()
            .lemma("plum")
            .build();
        ContentWord apple = ContentWord.builder()
            .lemma("apple")
            .build();
        ContentWord fruit = ContentWord.builder()
            .lemma("fruit")
            .build();

        ContentWord mix = ContentWord.builder()
            .lemma("mix")
            .build();
        ContentWord mash = ContentWord.builder()
            .lemma("mash")
            .build();
        ContentWord stash = ContentWord.builder()
            .lemma("stash")
            .build();
        ContentWord boil = ContentWord.builder()
            .lemma("boil")
            .build();
        ContentWord fry = ContentWord.builder()
            .lemma("fry")
            .build();

        ContentWord healthy = ContentWord.builder()
            .lemma("healthy")
            .build();
        ContentWord tasty = ContentWord.builder()
            .lemma("tasty")
            .build();
        ContentWord greasy = ContentWord.builder()
            .lemma("greasy")
            .build();
        ContentWord oily = ContentWord.builder()
            .lemma("oily")
            .build();
        ContentWord spicy = ContentWord.builder()
            .lemma("spicy")
            .build();

        StopWord a = StopWord.builder()
            .lemma("a")
            .build();
        StopWord it = StopWord.builder()
            .lemma("it")
            .build();
        StopWord be= StopWord.builder()
            .lemma("be")
            .build();
        StopWord is = StopWord.builder()
            .lemma("is")
            .build();
        StopWord must = StopWord.builder()
            .lemma("must")
            .build();
        StopWord and = StopWord.builder()
            .lemma("and")
            .build();
        StopWord to = StopWord.builder()
            .lemma("to")
            .build();
        StopWord oh = StopWord.builder()
            .lemma("oh")
            .build();

        HashMap<String, ContentWord> words = new HashMap<>();
        List<ContentWord> wordList = Arrays.asList(banana, kiwi, plum, apple, fruit, mix, mash, stash, boil, fry, healthy, tasty, greasy, oily, spicy);
        for (ContentWord word : wordList) {
            word.setTopicProbabilities(dummyTopicProbabilities);
            words.put(word.getLemma(), word);
        }

        // Topic-ContentWord Probabilities
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

        HashMap<String, DtoDocument> documents = new HashMap<>();
        DtoDocument banana_cake = DtoDocument.builder()
            .title("banana_cake")
            .tokens(Arrays.asList(
                ContentToken.builder()
                    .word(banana)
                    .topic(topic0)
                    .build(),
                StopToken.builder()
                    .word(must)
                    .build(),
                ContentToken.builder()
                    .word(fry)
                    .topic(topic1)
                    .build(),
                StopToken.builder()
                    .word(to)
                    .build(),
                StopToken.builder()
                    .word(be)
                    .build(),
                ContentToken.builder()
                    .word(oily)
                    .topic(topic2)
                    .build(),
                StopToken.builder()
                    .word(and)
                    .build(),
                ContentToken.builder()
                    .word(tasty)
                    .topic(topic2)
                    .build()))
            .build();
        DtoDocument church_song = DtoDocument.builder()
            .title("church_song")
            .tokens(Arrays.asList(
                StopToken.builder()
                    .word(oh)
                    .build(),
                ContentToken.builder()
                    .word(fruit)
                    .topic(topic0)
                    .build(),
                ContentToken.builder()
                    .word(fry)
                    .topic(topic1)
                    .build(),
                ContentToken.builder()
                    .word(stash)
                    .topic(topic1)
                    .build(),
                StopToken.builder()
                    .word(and)
                    .build(),
                ContentToken.builder()
                    .word(boil)
                    .topic(topic1)
                    .build(),
                StopToken.builder()
                    .word(it)
                    .build(),
                StopToken.builder()
                    .word(is)
                    .build(),
                ContentToken.builder()
                    .word(oily)
                    .topic(topic2)
                    .build()))
            .build();
        DtoDocument wok_manual = DtoDocument.builder()
            .title("wok_manual")
            .tokens(Arrays.asList(
                ContentToken.builder()
                    .word(plum)
                    .topic(topic0)
                    .build(),
                ContentToken.builder()
                    .word(fry)
                    .topic(topic1)
                    .build(),
                StopToken.builder()
                    .word(it)
                    .build(),
                ContentToken.builder()
                    .word(boil)
                    .topic(topic1)
                    .build(),
                StopToken.builder()
                    .word(it)
                    .build(),
                StopToken.builder()
                    .word(and)
                    .build(),
                StopToken.builder()
                    .word(it)
                    .build(),
                StopToken.builder()
                    .word(is)
                    .build(),
                ContentToken.builder()
                    .word(greasy)
                    .topic(topic2)
                    .build(),
                StopToken.builder()
                    .word(and)
                    .build(),
                ContentToken.builder()
                    .word(tasty)
                    .topic(topic2)
                    .build()))
            .build();
        List<DtoDocument> documentList = Arrays.asList(
            banana_cake,
            church_song,
            wok_manual);

        for (DtoDocument document : documentList) {
            document.setTopicProbabilities(dummyTopicProbabilities);
            documents.put(document.getTitle(), document);
        }

        // Topic-CorpusDocument Probabilities
        // NOTE: In real life, there would be on List of documentProbabilities for each Topic, but here we'll just assign the same dummy documentProbabilities to each word
        List<DocumentProbability> dummyDocumentProbabilities = Arrays.asList(
            DocumentProbability.builder()
                .document(banana_cake)
                .probability(.45)
                .build(),
            DocumentProbability.builder()
                .document(church_song)
                .probability(.30)
                .build(),
            DocumentProbability.builder()
                .document(wok_manual)
                .probability(.25)
                .build());

        topic0.setWordProbabilities(wordProbabilities0);
        topic1.setWordProbabilities(wordProbabilities1);
        topic2.setWordProbabilities(wordProbabilities2);
        for (Topic topic : topics) {
            topic.setDocumentProbabilities(dummyDocumentProbabilities);
        }

        DtoLda lda = DtoLda.builder()
            .corpusName(corpusName)
            .numberOfTopics(3)
            .documents(documents)
            .topics(topics)
            .words(words)
            .build();

        return lda;
    }
}
