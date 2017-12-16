package org.tw.neinkeinkaffee.lda.model.corpus;

import org.junit.Test;
import org.tw.neinkeinkaffee.lda.model.dto.word.StopWord;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CorpusDocumentTest {
	private static final String DOCUMENT_STRING = "中庸論|張爾岐|中庸之見尊於天下也久矣。而小人每竊其說以便其私。_";
	private static final List<String> STOPWORDS_LIST = Arrays.asList("而", "可", "其", "也", "矣", "以", "於", "于", "曰",
		"則", "者", "之", "不", "有", "為", "人", "無", "所", "此", "至", "亦", "自", "如", "必", "是", "又", "然", "在");

	@Test
	public void shouldParseDocumentFromString() {
		CorpusDocument document = CorpusDocument.fromString(DOCUMENT_STRING, STOPWORDS_LIST);
		assertThat(document.getWords().size(), equalTo(25));
		List<Word> stopWords = document.getWords().stream()
			.filter(word -> word.isStopword())
			.collect(Collectors.toList());
		assertThat(stopWords.size(), is(12));
		assertThat(stopWords.get(11).getLemma(), is("\n"));
	}
}