package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Token {
	// TODO: field can be just the lemma, except we want straightforward linking of lemma in document with information as to which other topics lemma is associated with?
	@Getter @Setter
	private Word word;
	@Getter @Setter
	private Topic topic;
}
