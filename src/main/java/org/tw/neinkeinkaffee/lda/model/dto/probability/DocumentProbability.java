package org.tw.neinkeinkaffee.lda.model.dto.probability;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;

@Builder
public class DocumentProbability implements Comparable<DocumentProbability> {
	@Getter
	private DtoDocument document;
	@Getter
	private Double probability;

	@Override
	public int compareTo(DocumentProbability other) {
		if (this.probability > other.probability)
			return 1;
		else if (this.probability < other.probability)
			return -1;
		else return 0;
	}
}
