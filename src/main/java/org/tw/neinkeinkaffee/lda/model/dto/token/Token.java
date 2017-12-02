package org.tw.neinkeinkaffee.lda.model.dto.token;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Token {
	@Getter
	private boolean stopToken;
}
