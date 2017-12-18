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
    @Getter @Setter
    private String corpusName;
    @Getter @Setter
    private int numberOfTopics;
}
