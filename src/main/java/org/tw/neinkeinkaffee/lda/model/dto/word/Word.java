package org.tw.neinkeinkaffee.lda.model.dto.word;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Word {
	@Getter
	private String lemma;
	@Getter
	private boolean stopWord;
}
