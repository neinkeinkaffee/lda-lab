package org.tw.neinkeinkaffee.lda.helper;

import org.springframework.stereotype.Component;
import org.tw.neinkeinkaffee.lda.model.Lda;
import org.tw.neinkeinkaffee.lda.model.Topic;

import java.util.Arrays;

@Component
public class SyntheticDataProvider {
    public Lda getByCorpusIdAndNumberOfTopics(int corpusId, int numberOfTopics) {
        // Parameters get ignored by the SyntheticDataProvider.
        // In the real application, the parameters will matter for retrieval from a proper repository with a composite
        // key, as described here: http://software-sympathy.blogspot.de/2017/01/spring-data-with-mongodb-and-composite.html
        // As for the Topic data structure, in the real application, the topWords should be inferred, not passed in when
        // building.
        Topic topic0 = Topic.builder()
            .id(0)
            .topWords(Arrays.asList("banana", "kiwi", "plum", "apple", "fruit"))
            .build();
        Topic topic1 = Topic.builder()
            .id(1)
            .topWords(Arrays.asList("mix", "mash", "stash", "boil", "fry"))
            .build();
        Topic topic2 = Topic.builder()
            .id(2)
            .topWords(Arrays.asList("healthy", "tasty", "greasy", "oily", "spicy"))
            .build();
        Lda lda = Lda.builder()
            .topics(Arrays.asList(topic0, topic1, topic2))
            .build();
        return lda;
    }
}
