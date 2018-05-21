package org.tw.neinkeinkaffee.lda.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.corpus.Word;
import org.tw.neinkeinkaffee.lda.dto.topic.Topic;

@Builder
public class Token {
	@Getter
	private boolean stopToken;
	@Getter
	private int topicId;
	@Getter
	private String lemma;
}
