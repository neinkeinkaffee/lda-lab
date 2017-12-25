package org.tw.neinkeinkaffee.lda.model.dto.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

@Builder
public class WordProbability implements Comparable<WordProbability> {
    @Getter private String lemma;
    @Getter private Double probability;

    @Override
    public int compareTo(WordProbability other) {
        if (this.probability > other.probability)
            return 1;
        else if (this.probability < other.probability)
            return -1;
        else
            return 0;
    }
}
