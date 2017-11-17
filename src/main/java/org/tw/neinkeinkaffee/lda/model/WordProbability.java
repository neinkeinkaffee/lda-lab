package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class WordProbability {
    @Getter private ContentWord word;
    @Getter private Double probability;
}
