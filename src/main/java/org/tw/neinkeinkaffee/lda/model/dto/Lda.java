package org.tw.neinkeinkaffee.lda.model.dto;

import lombok.Builder;
import lombok.Getter;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;

import java.util.HashMap;
import java.util.List;

@Builder
public class Lda {
    @Getter
    private List<Topic> topics;
	@Getter
	HashMap<String, ContentWord> words;
	@Getter
	HashMap<String, DtoDocument> documents;

//	public static Lda initFromCorpus(Corpus corpus, int numberOfTopics) {
//		Random randomNumberGenerator = new Random();
//		LdaBuilder ldaBuilder = Lda.builder();
//		HashMap<String, CorpusDocument> documents = new HashMap<String, CorpusDocument>();
//		for (CorpusDocument corpusDocument : corpus.getDocuments()) {
//			CorpusDocument.LdaDocumentBuilder ldaDocumentBuilder = CorpusDocument.builder();
//			for (Word word : corpusDocument.getTokens()) {
//				LdaToken token;
//				// TODO: having different classes for content and stop token makes the code more verbose
//				if (word.isStopWord()) {
//					token = StopToken.builder()
//						.word(StopWord.builder()
//							.lemma(word.getLemma())
//							.build())
//						.stopToken(true)
//						.build();
//				}
//				else {
//					Topic randomTopic = Topic.builder()
//						.id(randomNumberGenerator.nextInt(numberOfTopics + 1))
//						.build();
//					token = ContentToken.builder()
//						.topic(randomTopic)
//						.word(ContentWord.builder()
//							.lemma(word.getLemma())
//							.build())
//						.stopToken(false)
//						.build();
//				}
//				ldaDocumentBuilder.token(token);
//			}
//			documents.put(corpusDocument.getTitle(), ldaDocumentBuilder.build());
//		}
//		return ldaBuilder
//			.documents(documents)
//			.build();
//	}
}
