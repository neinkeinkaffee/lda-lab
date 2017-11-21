package org.tw.neinkeinkaffee.lda.model.word;

import lombok.Builder;

public class StopWord extends Word {

	@Builder
	private StopWord(String lemma) {
		super(lemma, true);
	}

	public static class StopWordBuilder extends WordBuilder {
		StopWordBuilder() {
			super();
		}
	}
}
