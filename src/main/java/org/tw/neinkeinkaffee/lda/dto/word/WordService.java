package org.tw.neinkeinkaffee.lda.dto.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {
    private ContentWordRepository contentWordRepository;

    @Autowired
    public WordService(final ContentWordRepository contentWordRepository) {
        this.contentWordRepository = contentWordRepository;
    }

    public List<ContentWord> fetchAllBy(String corpusName, int numberOfTopics, String timestamp) {
        return contentWordRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public ContentWord fetchBy(String corpusName, int numberOfTopics, String timestamp, String lemma) {
        return contentWordRepository.findByCorpusNameAndNumberOfTopicsAndTimestampAndLemma(corpusName, numberOfTopics, timestamp, lemma);
    }
}
