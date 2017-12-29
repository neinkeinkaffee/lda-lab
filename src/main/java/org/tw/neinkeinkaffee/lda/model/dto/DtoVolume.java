package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
public class DtoVolume {
    @Getter
    private String title;
    @Singular @Getter
    private List<DtoDocument> documents;
}
