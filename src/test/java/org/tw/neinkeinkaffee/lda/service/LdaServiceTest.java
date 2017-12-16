package org.tw.neinkeinkaffee.lda.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tw.neinkeinkaffee.lda.model.corpus.Corpus;
import org.tw.neinkeinkaffee.lda.model.dto.DtoLda;
import org.tw.neinkeinkaffee.lda.model.lda.Lda;
import org.tw.neinkeinkaffee.lda.repository.LdaRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.core.Is.is;

public class LdaServiceTest {

    @Mock
    LdaRepository ldaRepository;
    @InjectMocks
    LdaService ldaService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldFetchDtoLda() {
        // given
        DtoLda expectedDtoLda = DtoLda.builder().corpusName("someCorpus").build();
        Lda lda = Lda.fromCorpus(Corpus.builder().name("someCorpus").build(), 3);
        when(ldaRepository.findByCorpusNameAndNumberOfTopics("someCorpus", 3))
            .thenReturn(lda);

        // when
        DtoLda dtoLda = ldaService.fetchBy("someCorpus", 3);

        // then
        assertThat(dtoLda.getCorpusName(), is("someCorpus"));
    }

}