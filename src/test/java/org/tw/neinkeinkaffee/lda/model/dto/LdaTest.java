package org.tw.neinkeinkaffee.lda.model.dto;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class LdaTest {

	@InjectMocks
	private Lda lda;
	private Corpus corpus;

	@Before
	public void setup() {
		initMocks(this);
//		corpus = Corpus.builder()
//			.name("someCorpus")
//			.document(Document.builder()
//				.name("someDocument")
//				.word(ContentWord.builder()
//					.lemma("tasty")
//					.build())
//				.word(ContentWord.builder()
//					.lemma("banana")
//					.build())
//				.word(ContentWord.builder()
//					.lemma("mash")
//					.build())
//				.build())
//			.build();
	}

//	@Test
//	public void shouldInitializeTokensWithRandomTopics () {
//		dto = Lda.initFromCorpus(corpus, 10);
//
//		// TODO: the casting isn't nice but can't getTopic() otherwise
//		ContentToken firstTokenInFirstDocument = (ContentToken) dto.getDocuments().get("someDocument").getTokens().get(0);
//		ContentToken secondTokenInFirstDocument = (ContentToken) dto.getDocuments().get("someDocument").getTokens().get(1);
//		ContentToken thirdTokenInFirstDocument = (ContentToken) dto.getDocuments().get("someDocument").getTokens().get(2);
//		assertEquals(firstTokenInFirstDocument.getWord().getLemma(), "tasty");
//		assertThat(firstTokenInFirstDocument.getTopic().getId(), Matchers.greaterThan(-1));
//		assertEquals(secondTokenInFirstDocument.getWord().getLemma(), "banana");
//		assertThat(secondTokenInFirstDocument.getTopic().getId(), Matchers.greaterThan(-1));
//		assertEquals(thirdTokenInFirstDocument.getWord().getLemma(), "mash");
//		assertThat(thirdTokenInFirstDocument.getTopic().getId(), Matchers.greaterThan(-1));
//	}
}