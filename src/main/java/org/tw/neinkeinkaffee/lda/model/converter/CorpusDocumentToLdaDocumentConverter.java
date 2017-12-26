package org.tw.neinkeinkaffee.lda.model.converter;

import lombok.NoArgsConstructor;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.model.lda.LdaDocument;
import org.tw.neinkeinkaffee.lda.model.lda.LdaToken;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class CorpusDocumentToLdaDocumentConverter implements Converter<CorpusDocument, LdaDocument> {
	@Override
	public LdaDocument convert(CorpusDocument corpusDocument) {
		String title = corpusDocument.getTitle();
		String author = corpusDocument.getAuthor();
		String volume = corpusDocument.getVolume();
		List<LdaToken> tokens = corpusDocument.getWords().stream()
			.map(word -> LdaToken.builder()
							.lemma(word.getLemma())
							.stopword(word.isStopword())
							.topic(-1)
							.build())
			.collect(Collectors.toList());
		return LdaDocument.builder()
			.title(title)
			.author(author)
			.volume(volume)
			.tokens(tokens)
			.build();
	}
}
