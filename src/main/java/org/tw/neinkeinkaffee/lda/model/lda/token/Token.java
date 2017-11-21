package org.tw.neinkeinkaffee.lda.model.lda.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.word.Word;

@Builder
public class Token {
	@Getter
	private boolean stopToken;
}
