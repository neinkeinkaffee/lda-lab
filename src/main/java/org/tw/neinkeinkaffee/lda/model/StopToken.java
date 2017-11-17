package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;

public class StopToken extends Token {
	// TODO: field can be just the lemma, except we want straightforward linking of lemma in document with information as to which other topics lemma is associated with?

	@Builder
	private StopToken(StopWord word) {
		super(word, true);
	}

	public static class StopTokenBuilder extends TokenBuilder{
		StopTokenBuilder() {
			super();
		}
	}
}
