package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoSection;
import org.tw.neinkeinkaffee.lda.model.dto.DtoVolume;
import org.tw.neinkeinkaffee.lda.repository.DocumentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentService(final DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DtoDocument> fetchAllBy(String corpusName, int numberOfTopics, String timestamp) {
        return documentRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public List<DtoSection> fetchAllByAndGroupBySectionAndVolume(String corpusName, int numberOfTopics, String timestamp) {
        HashMap<String, List<DtoDocument>> volumes = new HashMap<String, List<DtoDocument>>(documentRepository
            .findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp).stream()
            .collect(groupingBy(DtoDocument::getVolume)));
        HashMap<String, List<DtoVolume>> sections = new HashMap<String, List<DtoVolume>>(volumes.entrySet().stream()
            .map(entry -> {
                String volumeTitle = entry.getKey();
                List<DtoDocument> volumeDocuments = entry.getValue();
                DtoVolume volume = DtoVolume.builder()
                    .title(volumeTitle)
                    .documents(volumeDocuments)
                    .build();
                return volume;
            })
            .collect(groupingBy(volume -> extractSectionTitleFromVolumeTitle(volume.getTitle()))));
        return sections.entrySet().stream()
            .map(entry -> DtoSection.builder()
                .title(entry.getKey())
                .volumes(entry.getValue())
                .build())
            .collect(Collectors.toList());
    }

    private String extractSectionTitleFromVolumeTitle(String volumeTitle) {
        Pattern sectionTitlePattern = Pattern.compile("卷[一二三四五六七八九十百]+(.*政|學術|治體)[一二三四五六七八九十百]+", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher sectionTitleMatcher = sectionTitlePattern.matcher(volumeTitle);
        String sectionTitle = sectionTitleMatcher.find() ? sectionTitleMatcher.group(1) : "FRONTMATTER";
        return sectionTitle;
    }

    public DtoDocument fetchBy(String corpusName, int numberOfTopics, String timestamp, String title) {
        return documentRepository.findByCorpusNameAndNumberOfTopicsAndTimestampAndTitle(corpusName, numberOfTopics, timestamp, title);
    }
}
