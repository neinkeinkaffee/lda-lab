package org.tw.neinkeinkaffee.lda.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.word.Word;

@Builder
public class Token {
	@Getter @Setter
	private Word word;
	@Getter
	private boolean stopToken;
}
