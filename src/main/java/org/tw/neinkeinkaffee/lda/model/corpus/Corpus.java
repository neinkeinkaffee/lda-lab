package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
public class Corpus {
	@Getter
	private String name;
	@Singular @Getter
	private List<CorpusDocument> documents;

	public static Corpus fromString(String corpusName, String corpusString, String stopwordsString) {
		List<String> lines = Arrays.asList(corpusString.split("\n"));
		List<String> stopwords = Arrays.asList(stopwordsString.split("\n"));
		List<CorpusDocument> documents = new ArrayList<>();
		for (String line : lines) {
			CorpusDocument document = CorpusDocument.fromString(line, stopwords);
			documents.add(document);
		}
		return Corpus.builder()
			.name(corpusName)
			.documents(documents)
			.build();
	}
}
