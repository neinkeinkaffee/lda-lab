package org.tw.neinkeinkaffee.lda.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tw.neinkeinkaffee.lda.model.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.topic.Topic;

public class ContentToken extends Token {
	// TODO: field can be just the lemma, except we want straightforward linking of lemma in document with information as to which other topics lemma is associated with?
	@Getter @Setter
	private Topic topic;

	@Builder
	private ContentToken(ContentWord word, Topic topic) {
		super(word, false);
		this.topic = topic;
	}

	public static class ContentTokenBuilder extends TokenBuilder{
		ContentTokenBuilder() {
			super();
		}
	}

	public boolean isContentToken() { return true; }
}