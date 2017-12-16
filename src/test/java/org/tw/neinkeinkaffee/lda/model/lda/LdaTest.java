package org.tw.neinkeinkaffee.lda.model.lda;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;
import org.tw.neinkeinkaffee.lda.model.corpus.Word;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.service.LdaService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LdaTest {

	private Lda lda;

	@Mock
	Corpus corpus;

	@Mock
	LdaService ldaService;

	@Before
	public void setup() {
		initMocks(this);
		when(corpus.getName()).thenReturn("someChineseCorpus");
		when(corpus.getDocuments()).thenReturn(Arrays.asList(corpusDocument));
		lda = Lda.fromCorpus(corpus, 2);
	}

	@Test
	public void shouldInitializeFromCorpus() {
		List<LdaDocument> documents = new ArrayList<>(lda.getDocuments().values());
		List<LdaToken> tokens = documents.get(0).getTokens();
		LdaToken stopToken = tokens.get(2);
		LdaToken contentToken = tokens.get(4);

		assertThat(documents.size(), is(1));
		assertThat(tokens.size(), is(5));

		assertThat(stopToken.getLemma(), is("之"));
		assertThat(stopToken.getTopic(), equalTo(-1));

		assertThat(contentToken.getLemma(), is("尊"));
		assertThat(contentToken.getTopic(), greaterThan(-1));

		HashMap<String, DtoDocument> dtoDocuments = lda.getDtoLda().getDocuments();
		assertThat(dtoDocuments.size(), is(1));
		assertThat(dtoDocuments.get("中庸").getTokens().get(4).isStopToken(), is(false));
	}

	private static final CorpusDocument corpusDocument = CorpusDocument.builder()
		.title("中庸")
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
}