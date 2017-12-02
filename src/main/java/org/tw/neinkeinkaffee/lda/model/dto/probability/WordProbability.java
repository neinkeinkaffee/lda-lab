package org.tw.neinkeinkaffee.lda.model.dto.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

@Builder
public class WordProbability {
    @Getter private ContentWord word;
    @Getter private Double probability;
}
