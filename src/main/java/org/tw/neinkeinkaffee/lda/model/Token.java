package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Token {
	@Getter @Setter
	private Word word;
	@Getter
	private boolean stopToken;
}
