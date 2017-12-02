package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Word {
	@Getter
	private String lemma;
	@Getter
	private boolean stopword;
}
