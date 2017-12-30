package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.model.dto.DtoSection;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.service.DocumentService;
import org.tw.neinkeinkaffee.lda.service.SectionService;
import org.tw.neinkeinkaffee.lda.service.TopicService;

import java.util.List;

@Controller
public class LdaController {
    private TopicService topicService;
    private SectionService sectionService;

    @Autowired
    public LdaController(final TopicService topicService, final SectionService sectionService) {
        this.topicService = topicService;
        this.sectionService = sectionService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}")
    String listTopicsAndSections(final @PathVariable("corpus_name") String corpusName,
                      final @PathVariable("number_of_topics") int numberOfTopics,
                      final @PathVariable("timestamp") String timestamp,
                      Model model) {
        List<Topic> topics = topicService.fetchAllBy(corpusName, numberOfTopics, timestamp);
        List<DtoSection> sections = sectionService.fetchAllByAndGroupBySectionAndVolume(corpusName, numberOfTopics, timestamp);
        model.addAttribute("corpusName", corpusName);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("topics", topics);
        model.addAttribute("sections", sections);
        return "lda";
    }
}
