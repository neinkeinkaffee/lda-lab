package org.tw.neinkeinkaffee.lda.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.tw.neinkeinkaffee.lda.model.dto.DtoDocument;
import org.tw.neinkeinkaffee.lda.model.dto.DtoSection;
import org.tw.neinkeinkaffee.lda.model.dto.DtoVolume;
import org.tw.neinkeinkaffee.lda.repository.DocumentRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.util.CollectionUtils.containsAny;

public class DocumentServiceTest {
    @Mock
    private DocumentRepository documentRepository;
    @InjectMocks
    private DocumentService documentService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldFetchAllByAndGroupBySectionAndVolume() throws Exception {
        DtoDocument document1 = DtoDocument.builder()
            .title("someTitle1")
            .volume("卷十二治體六治法下")
            .build();
        DtoDocument document2 = DtoDocument.builder()
            .title("someTitle2")
            .volume("卷三十七戶政十二農政中")
            .build();
        DtoDocument document3 = DtoDocument.builder()
            .title("someTitle3")
            .volume("FRONTMATTER")
            .build();
        DtoDocument document4 = DtoDocument.builder()
            .title("someTitle4")
            .volume("卷一學術一原學")
            .build();
        List<DtoDocument> documents = Arrays.asList(document1,
            document2,
            document3,
            document4);
//        List<DtoSection> expectedSections = Arrays.asList(DtoSection.builder()
//                .title("治體")
//                .volume(DtoVolume.builder()
//                    .title("卷十二治體六治法下")
//                    .document(document1)
//                    .build())
//                .build(),
//            DtoSection.builder()
//                .title("戶政")
//                .volume(DtoVolume.builder()
//                        .title("卷三十七戶政十二農政中")
//                        .document(document2)
//                        .build())
//                .build(),
//            DtoSection.builder()
//                .title("FRONTMATTER")
//                .volume(DtoVolume.builder()
//                    .title("FRONTMATTER")
//                    .document(document3)
//                    .build())
//                .build(),
//            DtoSection.builder()
//                .title("學術")
//                .volume(DtoVolume.builder()
//                    .title("卷一學術一原學")
//                    .document(document4)
//                    .build())
//                .build());
        Instant timestamp = Instant.now();
        when(documentRepository.findAllByCorpusNameAndNumberOfTopicsAndTimestamp("someCorpusName", 10, timestamp))
            .thenReturn(documents);
        List<String> fetchedSectionTitles = documentService.fetchAllByAndGroupBySectionAndVolume("someCorpusName", 10, timestamp).stream().map(section -> section.getTitle()).collect(Collectors.toList());
        assertTrue(fetchedSectionTitles.contains("學術"));
        assertTrue(fetchedSectionTitles.contains("治體"));
        assertTrue(fetchedSectionTitles.contains("戶政"));
        assertTrue(fetchedSectionTitles.contains("FRONTMATTER"));
    }

}