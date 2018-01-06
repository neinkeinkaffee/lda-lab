package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Builder
public class CorpusDocument {
    @Id
    private String id;
    @Getter @Setter
    private String corpusName;
    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    private String volume;
    @Singular
    @Getter
    private List<Word> words;

    public static CorpusDocument fromString(String documentString, List<String> stopwords) {
//        documentString = documentString.replaceAll("\\?", "？");
        String[] document_data = splitTitleAuthorText(documentString);
        String author = document_data[1];
        String title = null;
        String text = null;
        String volume = null;
        if (document_data.length == 5) {
            volume = document_data[2] + "·" + document_data[3];
            title = document_data[0] + "（" + volume + "）";
            text = document_data[4];
        }
        else {
            title = document_data[0];
            text = document_data[2];
        }
        List<Word> words = parseWords(stopwords, text);
        return CorpusDocument.builder()
            .title(title)
            .author(author)
            .words(words)
            .volume(volume)
            .build();
    }

    private static String[] splitTitleAuthorText(String documentString) {
        String[] title_author_text = documentString.split("[|]");
        if (title_author_text.length != 3 && title_author_text.length != 5) {
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
