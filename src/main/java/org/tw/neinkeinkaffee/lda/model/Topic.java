package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Topic {
    @Getter
    private Integer id;
    @Getter
    private final List<Word> wordTopicProbabilities;
    @Getter(lazy=true)
    private final List<Word> topWordTopicProbabilities = getTopNWordTopicProbabilities();

    private List<Word> getTopNWordTopicProbabilities() {
        return new ArrayList<Word>(wordTopicProbabilities.subList(0, 5));
    }
}
