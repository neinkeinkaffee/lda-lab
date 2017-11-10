package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class WordProbability {
    @Getter private Word word;
    @Getter private Double probability;
}
