package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.tw.neinkeinkaffee.lda.model.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.WordProbability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    private String id;
    @Setter
    private String corpusName;
    @Setter
    private int numberOfTopics;
    @Getter
    private int topicId;
    @Getter @Setter
    private List<WordProbability> wordProbabilities;
    @Getter(lazy=true)
    private final List<WordProbability> topWordProbabilities = getTopNWordProbabilities(10);
    @Getter(lazy=true)
    private final String topWordsSignature = getTopNWordsSignature(10);
    @Getter
    private String topWords;
    @Getter @Setter
    private List<DocumentProbability> documentProbabilities;
    @Getter(lazy=true)
    private final List<DocumentProbability> topDocumentProbabilities = getTopNDocumentProbabilities(5);

    private List<WordProbability> getTopNWordProbabilities(int n) {
        int numberOfDocuments = documentProbabilities.size();
        n = (n < numberOfDocuments) ? n : numberOfDocuments;
        return new ArrayList<WordProbability>(wordProbabilities.subList(0, n));
    }

    private List<DocumentProbability> getTopNDocumentProbabilities(int n) {
        int numberOfDocuments = documentProbabilities.size();
        n = (n < numberOfDocuments) ? n : numberOfDocuments;
        return new ArrayList<DocumentProbability>(documentProbabilities.subList(0, n));
    }

    private String getTopNWordsSignature(int n) {
        return getTopNWordProbabilities(n).stream()
            .map(wordProbability -> wordProbability.getWord().getLemma())
            .collect(joining(" "));
    }
}
