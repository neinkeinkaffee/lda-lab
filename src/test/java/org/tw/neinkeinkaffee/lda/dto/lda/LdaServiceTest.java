package org.tw.neinkeinkaffee.lda.dto.lda;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tw.neinkeinkaffee.lda.dto.document.DtoDocument;
import org.tw.neinkeinkaffee.lda.dto.word.ContentWordRepository;
import org.tw.neinkeinkaffee.lda.dto.document.DocumentRepository;
import org.tw.neinkeinkaffee.lda.dto.topic.TopicRepository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LdaServiceTest {

    @Mock
    TopicRepository topicRepository;
    @Mock
    ContentWordRepository contentWordRepository;
    @Mock
    DocumentRepository documentRepository;

    @InjectMocks
    LdaService ldaService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldFetchDtoLda() {
        // given
        DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("MMM-d-yyyy-hh:mm-a")
            .withZone(ZoneId.systemDefault());
        String instant = formatter.format(Instant.now());
        DtoLda expectedDtoLda = DtoLda.builder()
            .corpusName("someCorpus")
            .numberOfTopics(3)
            .timestamp(instant)
            .build();
        when(topicRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp("someCorpus", 3, instant))
            .thenReturn(new ArrayList<>());
        when(contentWordRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp("someCorpus", 3, instant))
            .thenReturn(new ArrayList<>());
        when(documentRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp("someCorpus", 3, instant))
            .thenReturn(new ArrayList<>());

        // when
        DtoLda dtoLda = ldaService.fetchBy("someCorpus", 3, instant);

        // then
        assertThat(dtoLda.getCorpusName(), is("someCorpus"));
        assertThat(dtoLda.getNumberOfTopics(), is(3));
    }

    @Test
    public void shouldGroupBySectionAndVolume() {
        DtoDocument document1 = DtoDocument.builder()
            .title("a")
            .volume("卷三十七戶政十二農政中")
            .build();
        DtoDocument document2 = DtoDocument.builder()
            .title("b")
            .volume("卷十二治體六治法下")
            .build();
        DtoDocument document3 = DtoDocument.builder()
            .title("c")
            .volume("卷十二治體六治法下")
            .build();
        DtoDocument document4 = DtoDocument.builder()
            .title("d")
            .volume("FRONTMATTER")
            .build();
        DtoDocument document5 = DtoDocument.builder()
            .title("e")
            .volume("卷一學術一原學")
            .build();
        List<DtoDocument> documents = Arrays.asList(document1, document2, document3, document4, document5);
        List<DtoDocument.DtoSection> sections = ldaService.groupBySectionAndVolume(documents);
        assertThat(sections.size(), is(4));
    }
}