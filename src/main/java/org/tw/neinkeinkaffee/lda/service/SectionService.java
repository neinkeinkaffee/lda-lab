package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.dto.DtoSection;
import org.tw.neinkeinkaffee.lda.repository.SectionRepository;

import java.util.List;

@Service
public class SectionService {
    private SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<DtoSection> fetchAllByAndGroupBySectionAndVolume(String corpusName, int numberOfTopics, String
        timestamp) {
        return sectionRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }
}