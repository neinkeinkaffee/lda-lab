package org.tw.neinkeinkaffee.lda.dto.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> fetchAllBy(String corpusName, int numberOfTopics, String timestamp) {
        return topicRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp(corpusName, numberOfTopics, timestamp);
    }

    public Topic fetchBy(String corpusName, int numberOfTopics, String timestamp, int topicId) {
        return topicRepository.findByCorpusNameAndNumberOfTopicsAndTimestampAndTopicId(corpusName, numberOfTopics, timestamp, topicId);
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }
}
