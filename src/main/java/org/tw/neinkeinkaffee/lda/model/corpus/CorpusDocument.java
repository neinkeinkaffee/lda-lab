package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Builder
public class CorpusDocument {
	@Getter
	private String title;
	@Getter
	private String author;
	@Singular
	@Getter
	private List<Word> words;

	public static CorpusDocument fromString(String documentString, List<String> stopwords) {
		String[] title_author_text = splitTitleAuthorText(documentString);
		String title = title_author_text[0];
		String author = title_author_text[1];
		String text = title_author_text[2];
		List<Word> words = parseWords(stopwords, text);
		return CorpusDocument.builder()
			.title(title)
			.author(author)
			.words(words)
			.build();
	}

	private static String[] splitTitleAuthorText(String documentString) {
		String[] title_author_text = documentString.split("[|]");
		if (title_author_text.length != 3) {
			int length = documentString.length() < 15 ? documentString.length() : 15;
			throw new IllegalArgumentException("String " + documentString.substring(0, length) + "... does not contain '|'");
		}
		return title_author_text;
	}

	private static List<Word> parseWords(List<String> stopwords, String text) {
		List<Word> words = Arrays.asList(text.split("")).stream()
			.map(w -> wordFromString(w, stopwords))
			.collect(toList());
		return words;
	}

	private static Word wordFromString(String string, List<String> stopwords) {
		Pattern punctuation = Pattern.compile("[\\p{Punct}|\\p{Space}]", Pattern.UNICODE_CHARACTER_CLASS);
		boolean isStopword = stopwords.contains(string) || punctuation.matcher(string).matches();
		if (string.equals("_")) string = "\n";
		return Word.builder()
			.lemma(string)
			.stopword(isStopword)
			.build();
	}
}
