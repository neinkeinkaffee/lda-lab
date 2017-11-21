package org.tw.neinkeinkaffee.lda.model.lda.token;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.word.StopWord;

public class StopToken extends Token {
	// TODO: field can be just the lemma, except we want straightforward linking of lemma in document with information as to which other topics lemma is associated with?
	@Getter
	private StopWord word;

	@Builder
	private StopToken(StopWord word) {
		super(true);
		this.word = word;
	}

	public static class StopTokenBuilder extends TokenBuilder{
		StopTokenBuilder() {
			super();
		}
	}
}
