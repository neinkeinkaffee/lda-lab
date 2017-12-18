package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Builder
public class DtoLda {

	@Getter
	private String corpusName;
	@Getter
	private int numberOfTopics;

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
