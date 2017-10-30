package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class Lda {
    private String id;
    @Getter
    private List<Topic> topics;
}
