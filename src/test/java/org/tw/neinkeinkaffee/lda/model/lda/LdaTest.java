package org.tw.neinkeinkaffee.lda.model.lda;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.model.corpus.Word;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LdaTest {

	private Lda lda;

	@Mock
	Corpus corpus;

	private static final CorpusDocument corpusDocument = CorpusDocument.builder()
		.word(Word.builder()
			.lemma("中")
			.stopword(false)
			.build())
		.word(Word.builder()
			.lemma("庸")
			.stopword(false)
			.build())
		.word(Word.builder()
			.lemma("之")
			.stopword(true)
			.build())
		.word(Word.builder()
			.lemma("見")
			.stopword(false)
			.build())
		.word(Word.builder()
			.lemma("尊")
			.stopword(false)
			.build())
		.build();

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void shouldInitializeDocumentsFromCorpus() {
		// given
		when(corpus.getDocuments()).thenReturn(Arrays.asList(corpusDocument));

		// when
		lda = Lda.fromCorpus(corpus, 10);

		// then
		List<LdaDocument> documents = lda.getDocuments();
		List<LdaToken> tokens = documents.get(0).getTokens();
		LdaToken fifthToken = tokens.get(4);

		assertThat(documents.size(), is(1));
		assertThat(tokens.size(), is(5));
		assertThat(fifthToken.getLemma(), is("尊"));
		assertThat(fifthToken.getTopic(), Matchers.greaterThanOrEqualTo(-1));
	}


}