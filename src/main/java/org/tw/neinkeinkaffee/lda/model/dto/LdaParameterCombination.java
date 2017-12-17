package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.*;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdaParameterCombination {
    @Getter
    private String corpusName;
    @Getter
    private int numberOfTopics;
}
