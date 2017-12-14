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
	@Id
	private String id;

	private String corpusName;
	private int numberOfTopics;

    @Getter
    private List<Topic> topics;
	@Getter
	private HashMap<String, ContentWord> words;
	@Getter
	private HashMap<String, DtoDocument> documents;
}
