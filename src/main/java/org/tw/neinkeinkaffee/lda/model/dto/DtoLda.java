package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

import java.time.Instant;
import java.util.List;

@Builder
public class DtoLda {

	@Getter
	private String corpusName;
	@Getter
	private int numberOfTopics;
	@Getter
	private Instant timestamp;

    @Getter
    private List<Topic> topics;
	@Getter
	private List<ContentWord> words;
	@Getter
	private List<DtoDocument> documents;

	public boolean isEmpty() {
		return documents.isEmpty();
	}
}
