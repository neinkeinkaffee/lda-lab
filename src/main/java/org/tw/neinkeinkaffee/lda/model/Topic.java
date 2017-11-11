package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Builder
public class Topic {
    @Getter
    private Integer id;
    @Getter @Setter
    private List<WordProbability> wordProbabilities;
    @Getter(lazy=true)
    private final List<WordProbability> topWordProbabilities = getTopNWordProbabilities();
    @Getter(lazy=true)
    private final String topWordsSignature = getTopNWordsSignature();

    private List<WordProbability> getTopNWordProbabilities() {
        return new ArrayList<WordProbability>(wordProbabilities.subList(0, 5));
    }

    private String getTopNWordsSignature() {
        return getTopNWordProbabilities().stream()
            .map(wordProbability -> wordProbability.getWord().getLemma())
            .collect(joining(" "));
    }
}
