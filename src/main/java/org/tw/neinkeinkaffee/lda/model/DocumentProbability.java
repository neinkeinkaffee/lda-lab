package org.tw.neinkeinkaffee.lda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class DocumentProbability {
	@Getter
	private Document document;
	@Getter
	private Double probability;
}
