package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LdaToken {
	@Getter
	private String lemma;
	@Getter @Setter
	private int topic;
	@Getter
	private boolean stopword;
}
