package org.tw.neinkeinkaffee.lda.helper;

import org.springframework.stereotype.Component;
import org.tw.neinkeinkaffee.lda.model.Lda;
import org.tw.neinkeinkaffee.lda.model.Topic;

import java.util.Arrays;

@Component
public class SyntheticDataProvider {
    public Lda getById(String ldaId) {
        // in the real application topWords should be inferred, not set with the builder
        Topic topic0 = Topic.builder()
            .id(0)
            .topWords("banana kiwi plum apple fruit")
            .build();
        Topic topic1 = Topic.builder()
            .id(1)
            .topWords("mix mash stash boil fry")
            .build();
        Topic topic2 = Topic.builder()
            .id(0)
            .topWords("healthy tasty greasy oily spicy")
            .build();
        // ldaId is ignored by the SyntheticDataProvider
        // in the real application, the ldaId will matter for retrieval from a proper repository
        Lda lda = Lda.builder()
            .topics(Arrays.asList(topic0, topic1, topic2))
            .build();
        return lda;
    }
}
