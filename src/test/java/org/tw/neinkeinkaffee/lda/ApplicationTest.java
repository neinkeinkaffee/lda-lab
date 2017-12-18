package org.tw.neinkeinkaffee.lda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
            String.class)).contains("You can explore");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/corpus/hcjswb/numberOfTopics/3/topic",
//            String.class)).contains("Topics");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/corpus/hcjswb/numberOfTopics/3/word/久",
//            String.class)).contains("Word");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/corpus/hcjswb/numberOfTopics/3/topic/0",
//            String.class)).contains("Topic");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/corpus/hcjswb/numberOfTopics/3/document/中庸論",
//            String.class)).contains("Document");
    }
}