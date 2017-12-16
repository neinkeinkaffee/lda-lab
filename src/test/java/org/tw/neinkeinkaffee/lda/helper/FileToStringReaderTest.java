package org.tw.neinkeinkaffee.lda.helper;

import org.junit.Test;

import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FileToStringReaderTest {

    @Test
    public void shouldReadFileToString() {
        String expectedFileContent = "中庸論|張爾岐|中庸之見尊於天下也久矣。而小人每竊其說以便其私。_\n" +
            "儲功篇上|陳遷鶴|讀書之士。喜虛名而不務實功。_\n";
        String fileContent = FileToStringReader.readFileToString("/Users/gstupper/repos/lda-lab/src/test/resources/corpora/hcjswb_micro.txt");
        assertThat(expectedFileContent, is(fileContent));
    }
}