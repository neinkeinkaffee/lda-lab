package org.tw.neinkeinkaffee.lda.model.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.model.token.Token;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Document {
	@Getter
	private String name;
	@Getter
	private List<Token> tokens;
	@Getter @Setter
	private List<TopicProbability> topicProbabilities;
	@Getter(lazy = true)
	private final List<TopicProbability> topTopicProbabilities = getTopNTopicProbabilities(5);

	private List<TopicProbability> getTopNTopicProbabilities(int N) {
		int endIndex = (N > topicProbabilities.size()) ? topicProbabilities.size() : N;
		return new ArrayList<TopicProbability>(topicProbabilities.subList(0, endIndex));
	}
}
