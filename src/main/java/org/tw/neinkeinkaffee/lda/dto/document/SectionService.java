package org.tw.neinkeinkaffee.lda.dto.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {
    private SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<DtoDocument.DtoSection> fetchAllByAndGroupBySectionAndVolume(String corpusName, int numberOfTopics, String
        timestamp) {
        return sectionRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }
}