package org.tw.neinkeinkaffee.lda.model.dto;

import com.sun.javafx.beans.IDProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.tw.neinkeinkaffee.lda.model.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.model.dto.token.Token;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoDocument {
	@Id
	private String id;
	@Setter
	String corpusName;
	@Setter
	int numberOfTopics;
	@Getter
	private String title;
	@Singular @Getter
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
