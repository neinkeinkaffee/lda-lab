package org.tw.neinkeinkaffee.lda.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tw.neinkeinkaffee.lda.model.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.model.dto.token.Token;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
@CompoundIndex(def = "{'corpusName':1, 'numberOfTopics':1, 'timestamp':-1, 'title':1}", name = "parameterCombination")
public class DtoDocument {
	@Id
	private String id;
	@Getter @Setter
	String corpusName;
	@Getter @Setter
	int numberOfTopics;
	@Getter
	private String author;
	@Getter
	String volume;
	@Getter @Setter
	String timestamp;
	@Getter
	private String title;

	@Singular @Getter
	private List<Token> tokens;
	@Getter @Setter
	private List<TopicProbability> topicProbabilities;
	@JsonIgnore @Getter(lazy = true)
	private final List<TopicProbability> topTopicProbabilities = getTopNTopicProbabilities(10);

	private List<TopicProbability> getTopNTopicProbabilities(int N) {
		int endIndex = (N > topicProbabilities.size()) ? topicProbabilities.size() : N;
		return new ArrayList<TopicProbability>(topicProbabilities.subList(0, endIndex));
	}
}
