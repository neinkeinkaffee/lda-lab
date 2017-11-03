package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tw.neinkeinkaffee.lda.model.Topic;
import org.tw.neinkeinkaffee.lda.service.LdaService;

import java.util.List;

@Controller
public class TopicController {
    private LdaService ldaService;

    @Autowired
    public TopicController(final LdaService ldaService) {
        this.ldaService = ldaService;
    }

    @RequestMapping("/{corpus_id}/{number_of_topics}/topic")
    String listTopics(final @PathVariable("corpus_id") String corpusId,
            final @PathVariable("number_of_topics") String numberOfTopics, Model model) {
        // TODO: Is there a better way to get from the combination of parameters of the model to an id?
        String ldaId = corpusId + numberOfTopics;
        List<Topic> topics = ldaService.findById(ldaId).getTopics();
        model.addAttribute("topics", topics);
        return "topic";
    }
}