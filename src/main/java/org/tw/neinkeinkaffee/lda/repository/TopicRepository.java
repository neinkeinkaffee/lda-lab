package org.tw.neinkeinkaffee.lda.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;

import java.util.List;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    public List<Topic> findAllByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
    public Topic findAllByCorpusNameAndNumberOfTopicsAndTopicId(String corpusName, int numberOfTopics, int topicId);

    public Long deleteByCorpusNameAndNumberOfTopics(String corpusName, int numberOfTopics);
}
