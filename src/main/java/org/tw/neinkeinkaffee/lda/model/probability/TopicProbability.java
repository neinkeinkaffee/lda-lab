package org.tw.neinkeinkaffee.lda.model.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.topic.Topic;

@Builder
public class TopicProbability {
	@Getter private Topic topic;
	@Getter private Double probability;
}
