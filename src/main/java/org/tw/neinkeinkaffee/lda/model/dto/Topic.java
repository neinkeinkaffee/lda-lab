package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.WordProbability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Builder
public class Topic {
    @Getter
    private Integer id;
    @Getter @Setter
    private List<WordProbability> wordProbabilities;
    @Getter(lazy=true)
    private final List<WordProbability> topWordProbabilities = getTopNWordProbabilities(10);
    @Getter(lazy=true)
    private final String topWordsSignature = getTopNWordsSignature(10);
    @Getter @Setter
    private List<DocumentProbability> documentProbabilities;
    @Getter(lazy=true)
    private final List<DocumentProbability> topDocumentProbabilities = getTopNDocumentProbabilities(5);

    private List<WordProbability> getTopNWordProbabilities(int n) {
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
