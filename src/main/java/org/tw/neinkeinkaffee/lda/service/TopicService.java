package org.tw.neinkeinkaffee.lda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.repository.TopicRepository;

import java.util.List;

@Service
public class TopicService {
    private TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> fetchAllBy(String corpusName, int numberOfTopics) {
        return topicRepository.findAllByCorpusNameAndNumberOfTopics(corpusName, numberOfTopics);
    }

    public Topic fetchBy(String corpusName, int numberOfTopics, int topicId) {
        return topicRepository.findAllByCorpusNameAndNumberOfTopicsAndTopicId(corpusName, numberOfTopics, topicId);
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }
}
