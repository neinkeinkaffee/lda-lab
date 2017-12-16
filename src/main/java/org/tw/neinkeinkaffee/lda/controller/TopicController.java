package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.service.LdaService;

@Controller
public class TopicController {
    private LdaService ldaService;

    @Autowired
    public TopicController(final LdaService ldaService) {
        this.ldaService = ldaService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/topic")
    String listTopics(final @PathVariable("corpus_name") String corpusName,
                      final @PathVariable("number_of_topics") int numberOfTopics,
                      Model model) {
//        System.out.println("PREFETCH size of repo: " + ldaService.fetchAll().size());
        DtoLda lda = ldaService.fetchBy(corpusName, numberOfTopics);
//        System.out.println("POSTFETCH size of repo: " + ldaService.fetchAll().size());
//        System.out.println("FETCHBY size of repo: " + ldaService.fetchBy(corpusName, numberOfTopics).equals(null));
        model.addAttribute("corpusName", corpusName);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("topics", lda.getTopics());
        return "topics";
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/topic/{topic_id}")
    String listTopic(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("topic_id") int topicId,
                     Model model) {
        DtoLda lda = ldaService.fetchBy(corpusName, numberOfTopics);
        model.addAttribute("corpusName", corpusName);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("topic", lda.getTopics().get(topicId));
        return "topic";
    }
}