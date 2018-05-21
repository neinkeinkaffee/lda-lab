package org.tw.neinkeinkaffee.lda.model.dto;

import org.junit.Test;
import org.tw.neinkeinkaffee.lda.dto.document.DtoDocument;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DtoVolumeTest {
    @Test
    public void shouldGetSection() throws Exception {
        DtoDocument.DtoVolume volume1 = DtoDocument.DtoVolume.builder()
            .title("卷十二治體六治法下")
            .build();
        DtoDocument.DtoVolume volume2 = DtoDocument.DtoVolume.builder()
            .title("卷三十七戶政十二農政中")
            .build();
        DtoDocument.DtoVolume volume3 = DtoDocument.DtoVolume.builder()
            .title("FRONTMATTER")
            .build();
        DtoDocument.DtoVolume volume4 = DtoDocument.DtoVolume.builder()
            .title("卷一學術一原學")
            .build();

        assertThat(volume1.getSection(), is("治體"));
        assertThat(volume2.getSection(), is("戶政"));
        assertThat(volume3.getSection(), is("FRONTMATTER"));
        assertThat(volume4.getSection(), is("學術"));
    }

}