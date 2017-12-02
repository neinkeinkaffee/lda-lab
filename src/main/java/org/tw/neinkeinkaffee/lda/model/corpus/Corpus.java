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
	private List<Document> documents;

	public static Corpus fromString(String corpusString, String stopwordsString) {
		List<String> lines = Arrays.asList(corpusString.split("\n"));
		List<String> stopwords = Arrays.asList(stopwordsString.split("\n"));
		List<Document> documents = new ArrayList<>();
		for (String line : lines) {
			Document document = Document.fromString(line, stopwords);
			documents.add(document);
		}
		return Corpus.builder().documents(documents).build();
	}
}
