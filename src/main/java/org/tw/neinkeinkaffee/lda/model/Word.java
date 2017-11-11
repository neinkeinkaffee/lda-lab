package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Word {
	@Getter
	private String lemma;
	@Getter @Setter
	private List<TopicProbability> topicProbabilities;
	@Getter(lazy=true)
	private final List<TopicProbability> topTopicProbabilities = getTopNTopicProbabilities();

	private List<TopicProbability> getTopNTopicProbabilities() {
		int N = 5;
		int endIndex = (N > topicProbabilities.size()) ? topicProbabilities.size() : N;
		return new ArrayList<TopicProbability>(topicProbabilities.subList(0, 3));
	}
}
