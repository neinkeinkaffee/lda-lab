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
    private final List<WordProbability> wordProbabilities;
    @Getter(lazy=true)
    private final List<WordProbability> topWordProbabilities = getTopNWordProbabilities();

    private List<WordProbability> getTopNWordProbabilities() {
        return new ArrayList<WordProbability>(wordProbabilities.subList(0, 5));
    }
}
