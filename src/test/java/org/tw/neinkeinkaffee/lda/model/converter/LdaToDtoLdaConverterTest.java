package org.tw.neinkeinkaffee.lda.model.converter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.dto.Topic;
import org.tw.neinkeinkaffee.lda.model.dto.probability.DocumentProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.TopicProbability;
import org.tw.neinkeinkaffee.lda.model.dto.probability.WordProbability;
import org.tw.neinkeinkaffee.lda.model.dto.word.ContentWord;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.model.lda.LdaDocument;
import org.tw.neinkeinkaffee.lda.model.lda.LdaToken;
import org.tw.neinkeinkaffee.lda.model.lda.PairCounter;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LdaToDtoLdaConverterTest {
    @Mock
    Lda lda;

    @InjectMocks
    LdaToDtoLdaConverter converter;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Ignore
    @Test
    public void shouldConvertLdaToDtoLda() throws Exception {

        //given

        PairCounter<Integer, String> topicWordCounts = new PairCounter<>();
        topicWordCounts.increaseByOne(0, "oneWord");
        topicWordCounts.increaseByOne(1, "anotherWord");
        when(lda.getTopicWordCounts()).thenReturn(topicWordCounts);

        LdaDocument document0 = LdaDocument.builder()
            .title("oneDocument")
            .token(LdaToken.builder()
                .stopword(false)
                .lemma("oneWord")
                .topic(0)
                .build())
            .token(LdaToken.builder()
                .stopword(true)
                .lemma("someStopWord")
                .build())
            .build();
        LdaDocument document1 = LdaDocument.builder()
            .title("someOtherDocument")
            .token(LdaToken.builder()
                .stopword(false)
                .lemma("someOtherWord")
                .topic(1)
                .build())
            .token(LdaToken.builder()
                .stopword(true)
                .lemma("someOtherStopWord")
                .build())
            .build();
        PairCounter<Integer, String> topicDocumentCounts = new PairCounter<>();
        topicDocumentCounts.increaseByOne(0, "oneDocument");
        topicDocumentCounts.increaseByOne(1, "someOtherDocument");
        when(lda.getTopicDocumentCounts()).thenReturn(topicDocumentCounts);

        PairCounter<String, Integer> wordTopicCounts = new PairCounter<>();
        wordTopicCounts.increaseByOne("oneWord", 0);
        wordTopicCounts.increaseByOne("anotherWord", 1);
        when(lda.getWordTopicCounts()).thenReturn(wordTopicCounts);

        PairCounter<String, Integer> documentTopicCounts = new PairCounter<>();
        documentTopicCounts.increaseByOne("oneDocument", 0);
        documentTopicCounts.increaseByOne("someOtherDocument", 1);
        when(lda.getDocumentTopicCounts()).thenReturn(documentTopicCounts);

        HashMap<String, LdaDocument> ldaDocuments = new HashMap<>();
        ldaDocuments.put("oneDocument", document0);
        ldaDocuments.put("someOtherDocument", document1);
        when(lda.getDocuments()).thenReturn(ldaDocuments);

        // when

        DtoLda dtoLda = converter.convert(lda);

        //then

        List<Topic> topics = dtoLda.getTopics();
        WordProbability topicWordProbability = topics.get(0).getWordProbabilities().get(0);
        assertThat(topicWordProbability.getWord().getLemma(), either(is("oneWord")).or(is("someOtherWord")));
        assertThat(topicWordProbability.getProbability(), is(closeTo(1.0, 0.01)));
        DocumentProbability topicDocumentProbability = topics.get(0).getDocumentProbabilities().get(0);
        assertThat(topicDocumentProbability.getDocument().getTitle(), either(is("oneDocument")).or(is("someOtherDocument")));
        assertThat(topicDocumentProbability.getProbability(), is(closeTo(1.0, 0.01)));

        HashMap<String, ContentWord> words = dtoLda.getWords();
        TopicProbability wordTopicProbability = words.get("oneWord").getTopicProbabilities().get(0);
        assertThat(wordTopicProbability.getTopic().getId(), is(0));
        assertThat(wordTopicProbability.getProbability(), is(closeTo(1.0, 0.01)));

        HashMap<String, DtoDocument> documents = dtoLda.getDocuments();
        TopicProbability documentTopicProbability = documents.get("oneDocument").getTopicProbabilities().get(0);
        assertThat(documentTopicProbability.getProbability(), is(closeTo(1.0, 0.01)));
    }

}