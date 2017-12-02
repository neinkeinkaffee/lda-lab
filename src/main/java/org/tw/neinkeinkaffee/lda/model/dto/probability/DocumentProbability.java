package org.tw.neinkeinkaffee.lda.model.dto.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;

@Builder
public class DocumentProbability {
	@Getter
	private DtoDocument document;
	@Getter
	private Double probability;
}
