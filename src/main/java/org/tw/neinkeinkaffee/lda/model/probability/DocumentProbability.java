package org.tw.neinkeinkaffee.lda.model.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.document.Document;

@Builder
public class DocumentProbability {
	@Getter
	private Document document;
	@Getter
	private Double probability;
}
