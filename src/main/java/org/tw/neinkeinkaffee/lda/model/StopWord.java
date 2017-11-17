package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;

public class StopWord extends Word {

	@Builder
	private StopWord(String lemma) {
		super(lemma);
	}

	public static class StopWordBuilder extends WordBuilder {
		StopWordBuilder() {
			super();
		}
	}
}
