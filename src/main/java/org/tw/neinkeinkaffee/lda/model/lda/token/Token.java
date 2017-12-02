package org.tw.neinkeinkaffee.lda.model.lda.token;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Token {
	@Getter
	private boolean stopToken;
}
