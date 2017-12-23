package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.time.Instant;

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
    private Instant timestamp;
}
