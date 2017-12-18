package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdaParameterCombination {
    @Id
    private String id;
    @Getter
    private String corpusName;
    @Getter
    private int numberOfTopics;
}
