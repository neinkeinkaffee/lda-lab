package org.tw.neinkeinkaffee.lda.model.lda;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.model.lda.document.LdaDocument;
import org.tw.neinkeinkaffee.lda.model.lda.token.ContentToken;
import org.tw.neinkeinkaffee.lda.model.lda.token.StopToken;
import org.tw.neinkeinkaffee.lda.model.lda.token.Token;
import org.tw.neinkeinkaffee.lda.model.lda.topic.Topic;
import org.tw.neinkeinkaffee.lda.model.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.word.StopWord;
import org.tw.neinkeinkaffee.lda.model.word.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Builder
public class Lda {
    private String id;
    @Getter
    private List<Topic> topics;
	@Getter
	HashMap<String, ContentWord> words;
	@Getter
	HashMap<String, LdaDocument> documents;

	public static Lda initFromCorpus(Corpus corpus, int numberOfTopics) {
		Random randomNumberGenerator = new Random();
		LdaBuilder ldaBuilder = Lda.builder();
		HashMap<String, LdaDocument> documents = new HashMap<String, LdaDocument>();
		for (CorpusDocument corpusDocument : corpus.getDocuments()) {
			LdaDocument.LdaDocumentBuilder ldaDocumentBuilder = LdaDocument.builder();
			for (Word word : corpusDocument.getWords()) {
				Token token;
				// TODO: having different classes for content and stop token makes the code more verbose
				if (word.isStopWord()) {
					token = StopToken.builder()
						.word(StopWord.builder()
							.lemma(word.getLemma())
							.build())
						.stopToken(true)
						.build();
				}
				else {
					Topic randomTopic = Topic.builder()
						.id(randomNumberGenerator.nextInt(numberOfTopics + 1))
						.build();
					token = ContentToken.builder()
						.topic(randomTopic)
						.word(ContentWord.builder()
							.lemma(word.getLemma())
							.build())
						.stopToken(false)
						.build();
				}
				ldaDocumentBuilder.token(token);
			}
			documents.put(corpusDocument.getName(), ldaDocumentBuilder.build());
		}
		return ldaBuilder
			.documents(documents)
			.build();
	}
}
