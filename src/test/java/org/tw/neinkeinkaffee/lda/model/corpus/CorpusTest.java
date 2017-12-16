package org.tw.neinkeinkaffee.lda.model.corpus;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CorpusTest {
	private static final String CORPUS_NAME = "hcjswb";
	private static final String CORPUS_STRING = "中庸論|張爾岐|中庸之見尊於天下也久矣。而小人每竊其說以便其私。\n" +
		"傳信錄序|方苞|古之所謂學者。將明諸心以盡在物之理而濟世用。\n" +
		"儲功篇上|陳遷鶴|讀書之士。喜虛名而不務實功。\n";
	private static final String STOPWORDS_STRING = "而\n可\n其\n也\n矣\n以\n於\n于\n曰\n則\n者\n之\n不\n有\n為\n人\n無\n" +
		"所\n此\n至\n亦\n自\n如\n必\n是\n又\n然\n在\n";

	@Test
	public void shouldParseCorpusFromString() {
		Corpus corpus = Corpus.fromString(CORPUS_NAME, CORPUS_STRING, STOPWORDS_STRING);
		assertThat(corpus.getName(), is(CORPUS_NAME));
		assertThat(corpus.getDocuments().size(), equalTo(3));
		assertThat(corpus.getDocuments().get(0).getWords().size(), equalTo(24));
		System.out.println(corpus.getDocuments().get(0).getWords().get(23).getLemma());

	}
}