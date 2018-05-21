package org.tw.neinkeinkaffee.lda.dto.lda;

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
    @Getter @Setter
    private String timestamp;
}
