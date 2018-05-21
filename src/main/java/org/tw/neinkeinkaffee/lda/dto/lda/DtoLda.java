package org.tw.neinkeinkaffee.lda.dto.lda;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.dto.topic.Topic;
import org.tw.neinkeinkaffee.lda.dto.document.DtoDocument;
import org.tw.neinkeinkaffee.lda.dto.word.ContentWord;

import java.util.List;

@Builder
public class DtoLda {

	@Getter
	private String corpusName;
	@Getter
	private int numberOfTopics;
	@Getter
	private String timestamp;

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
