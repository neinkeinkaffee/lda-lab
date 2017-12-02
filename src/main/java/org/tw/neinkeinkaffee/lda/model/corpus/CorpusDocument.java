package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Builder
public class CorpusDocument {
	@Getter
	private String title;
	@Getter
	private String author;
	@Singular
	@Getter
	private List<CorpusToken> tokens;

	private static final String SINGLE_NON_WHITESPACE_CHAR = "[\\S]";

	public static CorpusDocument fromString(String documentString, List<String> stopwords) {
		String[] title_author_text = splitTitleAuthorText(documentString);
		String title = title_author_text[0];
		String author = title_author_text[1];
		String text = title_author_text[2];
		List<CorpusToken> tokens = parseTokens(stopwords, text);
		return CorpusDocument.builder()
			.title(title)
			.author(author)
			.tokens(tokens)
			.build();
	}

	private static String[] splitTitleAuthorText(String documentString) {
		String[] title_author_text = documentString.split("[|]");
		if (title_author_text.length != 3) {
			throw new IllegalArgumentException("String " + documentString.substring(0, 15) + "... does not contain '|'");
		}
		return title_author_text;
	}

	private static List<CorpusToken> parseTokens(List<String> stopwords, String text) {
		List<CorpusToken> tokens = Arrays.asList(text.split("")).stream()
			.map(w -> tokenFromWord(w, stopwords))
			.collect(toList());
		return tokens;
	}

	private static CorpusToken tokenFromWord(String word, List<String> stopwords) {
		boolean isStopword = stopwords.contains(word);
		return CorpusToken.builder()
			.word(word)
			.stopword(isStopword)
			.build();
	}
}
