package org.tw.neinkeinkaffee.lda.model.dto.word;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.tw.neinkeinkaffee.lda.model.dto.probability.TopicProbability;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ContentWord extends Word {
	@Id
	private String id;
	@Setter
	private String corpusName;
	@Setter
	private int numberOfTopics;
	@Getter @Setter
	private List<TopicProbability> topicProbabilities;
	@Getter(lazy=true)
	private final List<TopicProbability> topTopicProbabilities = getTopNTopicProbabilities(5);

	@Builder
	private ContentWord(String lemma, List<TopicProbability> topicProbabilities) {
		super(lemma, false);
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
