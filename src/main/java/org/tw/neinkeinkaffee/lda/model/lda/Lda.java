package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tw.neinkeinkaffee.lda.model.converter.CorpusToLdaDocumentConverter;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class Lda {
	private static CorpusToLdaDocumentConverter documentConverter = new CorpusToLdaDocumentConverter();

	@Getter
	private List<LdaDocument> documents;
	@Getter
	private int numberOfTopics;

	public static Lda fromCorpus(Corpus corpus, int numberOfTopics) {
		List<LdaDocument> documents = corpus.getDocuments().stream()
			.map(document -> documentConverter.convert(document))
			.collect(Collectors.toList());
		return Lda.builder()
			.documents(documents)
			.numberOfTopics(numberOfTopics)
			.build();
	}
}
