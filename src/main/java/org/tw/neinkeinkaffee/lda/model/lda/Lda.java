package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.topic.Topic;
import org.tw.neinkeinkaffee.lda.model.document.Document;
import org.tw.neinkeinkaffee.lda.model.word.ContentWord;

import java.util.HashMap;
import java.util.List;

@Builder
public class Lda {
    private String id;
    @Getter
    private List<Topic> topics;
	@Getter
	HashMap<String, ContentWord> words;
	@Getter
	HashMap<String, Document> documents;
}
