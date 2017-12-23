package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdaCounters {
    @Getter
    private String corpusName;
    @Getter
    private int numberOfTopics;
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
}
