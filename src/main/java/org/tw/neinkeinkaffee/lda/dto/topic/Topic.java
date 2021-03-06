package org.tw.neinkeinkaffee.lda.dto.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.tw.neinkeinkaffee.lda.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.dto.probability.WordProbability;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    private String id;

    @Getter @Setter
    private String corpusName;
    @Getter @Setter
    private int numberOfTopics;
    @Getter @Setter
    private String timestamp;
    @Getter
    private int topicId;

    @Getter @Setter
    private List<WordProbability> wordProbabilities;
    @JsonIgnore @Getter(lazy=true)
    private final List<WordProbability> topWordProbabilities = getTopNWordProbabilities(10);
    @JsonIgnore @Getter(lazy=true)
    private final String topWordsSignature = getTopNWordsSignature(10);
    @Getter
    private String topWords;
    @Getter @Setter
    private List<WordProbability> multiWordProbabilities;
    @JsonIgnore @Getter(lazy=true)
    private final List<WordProbability> topMultiWordProbabilities = getTopNMultiWordProbabilities(10);

    @Getter @Setter
    private List<DocumentProbability> documentProbabilities;
    @JsonIgnore @Getter(lazy=true)
    private final List<DocumentProbability> topDocumentProbabilities = getTopNDocumentProbabilities(5);

    private List<WordProbability> getTopNWordProbabilities(int n) {
        int numberOfWords = wordProbabilities.size();
        n = (n < numberOfWords) ? n : numberOfWords;
        return new ArrayList<WordProbability>(wordProbabilities.subList(0, n));
    }

    private List<WordProbability> getTopNMultiWordProbabilities(int n) {
        int numberOfMultiWords = multiWordProbabilities.size();
        n = (n < numberOfMultiWords) ? n : numberOfMultiWords;
        return new ArrayList<WordProbability>(multiWordProbabilities.subList(0, n));
    }

    private List<DocumentProbability> getTopNDocumentProbabilities(int n) {
        int numberOfDocuments = documentProbabilities.size();
        n = (n < numberOfDocuments) ? n : numberOfDocuments;
        return new ArrayList<DocumentProbability>(documentProbabilities.subList(0, n));
    }

    private String getTopNWordsSignature(int n) {
        return getTopNWordProbabilities(n).stream()
            .map(wordProbability -> wordProbability.getLemma())
            .collect(joining(" "));
    }
}
