package org.tw.neinkeinkaffee.lda.model.lda.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.lda.topic.Topic;

@Builder
public class TopicProbability {
	@Getter private Topic topic;
	@Getter private Double probability;
}
