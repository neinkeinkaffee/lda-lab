package org.tw.neinkeinkaffee.lda.model.corpus;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
public class Corpus {
	@Getter
	private String name;
	@Singular @Getter
	private List<CorpusDocument> documents;
}
