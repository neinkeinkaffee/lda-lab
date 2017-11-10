package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class TopicProbability {
	@Getter private Topic topic;
	@Getter private Double probability;
}
