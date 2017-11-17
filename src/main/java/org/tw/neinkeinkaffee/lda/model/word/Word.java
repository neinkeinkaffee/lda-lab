package org.tw.neinkeinkaffee.lda.model.word;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Word {
	@Getter
	private String lemma;
}
