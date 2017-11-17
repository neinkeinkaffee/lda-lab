package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Word {
	@Getter
	private String lemma;
}
