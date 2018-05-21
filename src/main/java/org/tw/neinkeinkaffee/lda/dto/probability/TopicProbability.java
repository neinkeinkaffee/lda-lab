package org.tw.neinkeinkaffee.lda.dto.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.dto.topic.Topic;

@Builder
public class TopicProbability implements Comparable<TopicProbability> {
	@Getter private Topic topic;
	@Getter private Double probability;

	@Override
	public int compareTo(TopicProbability other) {
		if (this.probability > other.probability)
			return 1;
		else if (this.probability < other.probability)
			return -1;
		else
			return 0;
	}
}
