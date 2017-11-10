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
    private final List<Word> words;
    @Getter(lazy=true)
    private final List<Word> topWords = getTopNWords();

    private List<Word> getTopNWords() {
        return new ArrayList<Word>(words.subList(0, 5));
    }
}
