package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.service.LdaService;

import java.util.UUID;

@Controller
public class TopicController {
    private LdaService ldaService;

    @Autowired
    public TopicController(final LdaService ldaService) {
        this.ldaService = ldaService;
    }

    @RequestMapping("/corpus/{corpus_id}/numberOfTopics/{number_of_topics}/topic")
    String listTopics(final @PathVariable("corpus_id") UUID corpusId,
                      final @PathVariable("number_of_topics") int numberOfTopics,
                      Model model) {
        Lda lda = ldaService.fetchBy(corpusId, numberOfTopics);
        model.addAttribute("corpusId", corpusId);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("topics", lda.getTopics());
        return "topics";
    }

    @RequestMapping("/corpus/{corpus_id}/numberOfTopics/{number_of_topics}/topic/{topic_id}")
    String listTopic(final @PathVariable("corpus_id") UUID corpusId,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("topic_id") int topicId,
                     Model model) {
        Lda lda = ldaService.fetchBy(corpusId, numberOfTopics);
        model.addAttribute("corpusId", corpusId);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("topic", lda.getTopics().get(topicId));
        return "topic";
    }
}