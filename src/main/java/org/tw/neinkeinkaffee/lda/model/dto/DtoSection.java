package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.text.Collator;
import java.util.List;
import java.util.Locale;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoSection implements Comparable<DtoSection> {
    @Id
    private String id;
    @Getter
    private String title;
    @Getter
    @Setter
    String corpusName;
    @Getter
    @Setter
    int numberOfTopics;
    @Getter @Setter
    String timestamp;
    @Getter
    private String author;
    @Singular
    @Getter
    private List<DtoVolume> volumes;

    @Override
    public int compareTo(DtoSection other) {
        Collator collator = Collator.getInstance(Locale.TRADITIONAL_CHINESE);
        return collator.compare(this.title, other.title);
    }
}
