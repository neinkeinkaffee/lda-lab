package org.tw.neinkeinkaffee.lda.helper;

import org.springframework.stereotype.Component;
import org.tw.neinkeinkaffee.lda.model.Lda;
import org.tw.neinkeinkaffee.lda.model.Topic;
import org.tw.neinkeinkaffee.lda.model.Word;

import java.util.Arrays;

@Component
public class SyntheticDataProvider {
    public Lda getByCorpusIdAndNumberOfTopics(int corpusId, int numberOfTopics) {
        // CorpusID and numberOfTopics get ignored by the SyntheticDataProvider.
        // TODO: CorpusID and numberOfTopics should be used to retrieve the lda model from a proper repository with a composite key, as described in http://software-sympathy.blogspot.de/2017/01/spring-data-with-mongodb-and-composite.html
        Topic topic0 = Topic.builder()
            .id(0)
            .words(Arrays.asList(
                Word.builder()
                    .lemma("banana")
                    .probability(.35)
                    .build(),
                Word.builder()
                    .lemma("kiwi")
                    .probability(.30)
                    .build(),
                Word.builder()
                    .lemma("plum")
                    .probability(.25)
                    .build(),
                Word.builder()
                    .lemma("apple")
                    .probability(.20)
                    .build(),
                Word.builder()
                    .lemma("fruit")
                    .probability(.15)
                    .build()))
            .build();

        Topic topic1 = Topic.builder()
            .id(1)
            .words(Arrays.asList(
                Word.builder()
                    .lemma("mix")
                    .probability(.30)
                    .build(),
                Word.builder()
                    .lemma("mash")
                    .probability(.28)
                    .build(),
                Word.builder()
                    .lemma("stash")
                    .probability(.25)
                    .build(),
                Word.builder()
                    .lemma("boil")
                    .probability(.15)
                    .build(),
                Word.builder()
                    .lemma("fry")
                    .probability(.2)
                    .build()))
            .build();

        Topic topic2 = Topic.builder()
            .id(2)
            .words(Arrays.asList(
                Word.builder()
                    .lemma("healthy")
                    .probability(.37)
                    .build(),
                Word.builder()
                    .lemma("tasty")
                    .probability(.28)
                    .build(),
                Word.builder()
                    .lemma("greasy")
                    .probability(.15)
                    .build(),
                Word.builder()
                    .lemma("oily")
                    .probability(.14)
                    .build(),
                Word.builder()
                    .lemma("spicy")
                    .probability(.6)
                    .build()))
            .build();

        Lda lda = Lda.builder()
            .topics(Arrays.asList(topic0, topic1, topic2))
            .build();

        return lda;
    }
}
