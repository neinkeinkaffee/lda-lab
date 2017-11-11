package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Setter;

@Builder
public class Token {
	@Setter
	private Word word;
	@Setter
	private Topic topic;
}
