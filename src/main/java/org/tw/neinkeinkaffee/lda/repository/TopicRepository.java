package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    public List<Topic> findAllByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
    public Topic findByCorpusNameAndNumberOfTopicsAndTimestampAndTopicId(String corpusName, int numberOfTopics, String timestamp, int topicId);
    public Long deleteByCorpusNameAndNumberOfTopicsAndTimestamp(String corpusName, int numberOfTopics, String timestamp);
}
