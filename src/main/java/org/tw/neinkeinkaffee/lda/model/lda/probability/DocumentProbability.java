package org.tw.neinkeinkaffee.lda.model.lda.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.lda.document.LdaDocument;

@Builder
public class DocumentProbability {
	@Getter
	private LdaDocument document;
	@Getter
	private Double probability;
}
