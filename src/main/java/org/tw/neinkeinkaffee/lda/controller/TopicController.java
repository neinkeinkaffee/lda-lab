package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.service.LdaService;
import org.tw.neinkeinkaffee.lda.service.TopicService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Controller
public class TopicController {
    private TopicService topicService;

    @Autowired
    public TopicController(final TopicService topicService) {
        this.topicService = topicService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/topic")
    String listTopics(final @PathVariable("corpus_name") String corpusName,
                      final @PathVariable("number_of_topics") int numberOfTopics,
                      final @PathVariable("timestamp") Instant timestamp,
                      Model model) {
        List<Topic> topics = topicService.fetchAllBy(corpusName, numberOfTopics, timestamp);
        model.addAttribute("corpusName", corpusName);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("topics", topics);
        return "topics";
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/topic/{topic_id}")
    String listTopic(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") Instant timestamp,
                     final @PathVariable("topic_id") int topicId,
                     Model model) {
        Topic topic = topicService.fetchBy(corpusName, numberOfTopics, timestamp, topicId);
        model.addAttribute("corpusName", corpusName);
        model.addAttribute("numberOfTopics", numberOfTopics);
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("topic", topic);
        return "topic";
    }
}