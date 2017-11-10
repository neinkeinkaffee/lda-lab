package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class WordProbability {
    @Getter private String lemma;
    @Getter private Double probability;
}
