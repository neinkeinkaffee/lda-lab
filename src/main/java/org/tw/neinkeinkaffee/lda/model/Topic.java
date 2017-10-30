package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Topic {
    @Getter
    private Integer id;
    @Getter
    private String topWords;
}
