package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;

@Builder
public class LdaToken {
	@Getter
	private String lemma;
	@Getter
	private int topic;
	@Getter
	private boolean stopword;
}
