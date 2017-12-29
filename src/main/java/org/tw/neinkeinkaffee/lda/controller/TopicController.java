package org.tw.neinkeinkaffee.lda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.service.TopicService;

import java.util.List;

@Controller
public class TopicController {
    private TopicService topicService;

    @Autowired
    public TopicController(final TopicService topicService) {
        this.topicService = topicService;
    }

    @RequestMapping("/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/topic/{topic_id}")
    String listTopic(final @PathVariable("corpus_name") String corpusName,
                     final @PathVariable("number_of_topics") int numberOfTopics,
                     final @PathVariable("timestamp") String timestamp,
                     final @PathVariable("topic_id") int topicId,
                     Model model) {
        Topic topic = topicService.fetchBy(corpusName, numberOfTopics, timestamp, topicId);
        model.addAttribute("topic", topic);
        return "topic";
    }

    @RequestMapping("/api/corpus/{corpus_name}/numberOfTopics/{number_of_topics}/timestamp/{timestamp}/topic/{topic_id}")
    @ResponseBody
    Topic listTopicJson(final @PathVariable("corpus_name") String corpusName,
                  final @PathVariable("number_of_topics") int numberOfTopics,
                  final @PathVariable("timestamp") String timestamp,
                  final @PathVariable("topic_id") int topicId,
                  Model model) {
        Topic topic = topicService.fetchBy(corpusName, numberOfTopics, timestamp, topicId);
        return topic;
    }
}