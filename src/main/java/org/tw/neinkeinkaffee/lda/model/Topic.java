package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class Topic {
    @Getter
    private Integer id;
    @Getter
    private final List<String> topWords;

}
