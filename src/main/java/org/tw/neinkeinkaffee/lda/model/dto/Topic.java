package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.WordProbability;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Builder
public class Topic {
    @Getter
    private Integer id;
    @Getter @Setter
    private List<WordProbability> wordProbabilities;
    @Getter(lazy=true)
    private final List<WordProbability> topWordProbabilities = getTopNWordProbabilities(5);
    @Getter(lazy=true)
    private final String topWordsSignature = getTopNWordsSignature(5);
    @Getter @Setter
    private List<DocumentProbability> documentProbabilities;
    @Getter(lazy=true)
    private final List<DocumentProbability> topDocumentProbabilities = getTopNDocumentProbabilities(3);

    private List<WordProbability> getTopNWordProbabilities(int N) {
        return new ArrayList<WordProbability>(wordProbabilities.subList(0, N));
    }

    private List<DocumentProbability> getTopNDocumentProbabilities(int N) {
        return new ArrayList<DocumentProbability>(documentProbabilities.subList(0, N));
    }

    private String getTopNWordsSignature(int N) {
        return getTopNWordProbabilities(N).stream()
            .map(wordProbability -> wordProbability.getWord().getLemma())
            .collect(joining(" "));
    }
}
