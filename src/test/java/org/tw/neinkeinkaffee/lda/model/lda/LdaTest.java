package org.tw.neinkeinkaffee.lda.model.lda;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.model.lda.token.ContentToken;
import org.tw.neinkeinkaffee.lda.model.word.ContentWord;

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
		corpus = Corpus.builder()
			.name("someCorpus")
			.document(CorpusDocument.builder()
				.name("someDocument")
				.word(ContentWord.builder()
					.lemma("tasty")
					.build())
				.word(ContentWord.builder()
					.lemma("banana")
					.build())
				.word(ContentWord.builder()
					.lemma("mash")
					.build())
				.build())
			.build();
	}

	@Test
	public void shouldInitializeTokensWithRandomTopics () {
		lda = Lda.initFromCorpus(corpus, 10);

		// TODO: the casting isn't nice but can't getTopic() otherwise
		ContentToken firstTokenInFirstDocument = (ContentToken) lda.getDocuments().get("someDocument").getTokens().get(0);
		ContentToken secondTokenInFirstDocument = (ContentToken) lda.getDocuments().get("someDocument").getTokens().get(1);
		ContentToken thirdTokenInFirstDocument = (ContentToken) lda.getDocuments().get("someDocument").getTokens().get(2);
		assertEquals(firstTokenInFirstDocument.getWord().getLemma(), "tasty");
		assertThat(firstTokenInFirstDocument.getTopic().getId(), Matchers.greaterThan(-1));
		assertEquals(secondTokenInFirstDocument.getWord().getLemma(), "banana");
		assertThat(secondTokenInFirstDocument.getTopic().getId(), Matchers.greaterThan(-1));
		assertEquals(thirdTokenInFirstDocument.getWord().getLemma(), "mash");
		assertThat(thirdTokenInFirstDocument.getTopic().getId(), Matchers.greaterThan(-1));
	}
}