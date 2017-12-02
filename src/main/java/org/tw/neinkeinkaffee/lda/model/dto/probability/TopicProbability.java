package org.tw.neinkeinkaffee.lda.model.dto.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;

@Builder
public class TopicProbability {
	@Getter private Topic topic;
	@Getter private Double probability;
}
