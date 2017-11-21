package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.tw.neinkeinkaffee.lda.model.word.Word;

import java.util.List;

@Builder
public class CorpusDocument {
	@Getter
	private String name;
	@Singular @Getter
	private List<Word> words;
}
