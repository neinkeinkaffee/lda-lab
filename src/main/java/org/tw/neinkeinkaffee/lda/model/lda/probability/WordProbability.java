package org.tw.neinkeinkaffee.lda.model.lda.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.word.ContentWord;

@Builder
public class WordProbability {
    @Getter private ContentWord word;
    @Getter private Double probability;
}
