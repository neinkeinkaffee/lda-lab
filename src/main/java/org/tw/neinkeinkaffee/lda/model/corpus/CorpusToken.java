package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;

@Builder
public class CorpusToken {
	@Getter
	private String word;
	@Getter
	private int topic;
	@Getter
	private boolean stopword;
}
