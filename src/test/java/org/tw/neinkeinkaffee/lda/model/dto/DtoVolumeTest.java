package org.tw.neinkeinkaffee.lda.model.dto;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DtoVolumeTest {
    @Test
    public void shouldGetSection() throws Exception {
        DtoVolume volume1 = DtoVolume.builder()
            .title("卷十二治體六治法下")
            .build();
        DtoVolume volume2 = DtoVolume.builder()
            .title("卷三十七戶政十二農政中")
            .build();
        DtoVolume volume3 = DtoVolume.builder()
            .title("FRONTMATTER")
            .build();
        DtoVolume volume4 = DtoVolume.builder()
            .title("卷一學術一原學")
            .build();

        assertThat(volume1.getSection(), is("治體"));
        assertThat(volume2.getSection(), is("戶政"));
        assertThat(volume3.getSection(), is("FRONTMATTER"));
        assertThat(volume4.getSection(), is("學術"));
    }

}