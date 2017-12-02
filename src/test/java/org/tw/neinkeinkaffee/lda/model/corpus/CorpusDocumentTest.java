package org.tw.neinkeinkaffee.lda.model.corpus;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CorpusDocumentTest {
	private static final String DOCUMENT_STRING = "中庸論|張爾岐|中庸之見尊於天下也久矣。而小人每竊其說以便其私。";
	private static final List<String> STOPWORDS_LIST = Arrays.asList("而", "可", "其", "也", "矣", "以", "於", "于", "曰",
		"則", "者", "之", "不", "有", "為", "人", "無", "所", "此", "至", "亦", "自", "如", "必", "是", "又", "然", "在");

	@Test
	public void shouldParseDocumentFromString() {
		Document document = Document.fromString(DOCUMENT_STRING, STOPWORDS_LIST);
		assertThat(document.getWords().size(), equalTo(24));
	}
}