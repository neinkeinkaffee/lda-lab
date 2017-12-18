package org.tw.neinkeinkaffee.lda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorpusName {
    @Id
    private String id;
    @Getter
    private String name;
}
