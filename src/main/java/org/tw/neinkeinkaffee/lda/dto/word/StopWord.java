package org.tw.neinkeinkaffee.lda.dto.word;

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
