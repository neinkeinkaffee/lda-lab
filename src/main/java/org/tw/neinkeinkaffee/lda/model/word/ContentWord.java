package org.tw.neinkeinkaffee.lda.model.word;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.probability.TopicProbability;

import java.util.ArrayList;
import java.util.List;

public class ContentWord extends Word {
	@Getter @Setter
	private List<TopicProbability> topicProbabilities;
	@Getter(lazy=true)
	private final List<TopicProbability> topTopicProbabilities = getTopNTopicProbabilities(5);

	@Builder
	private ContentWord(String lemma, List<TopicProbability> topicProbabilities) {
		super(lemma);
		this.topicProbabilities = topicProbabilities;
	}

	public static class ContentWordBuilder extends WordBuilder {
		ContentWordBuilder() {
			super();
		}
	}

	private List<TopicProbability> getTopNTopicProbabilities(int N) {
		int endIndex = (N > topicProbabilities.size()) ? topicProbabilities.size() : N;
		return new ArrayList<TopicProbability>(topicProbabilities.subList(0, endIndex));
	}
}
