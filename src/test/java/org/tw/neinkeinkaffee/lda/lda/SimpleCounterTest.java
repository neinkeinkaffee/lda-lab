package org.tw.neinkeinkaffee.lda.lda;

import org.junit.Test;
import org.tw.neinkeinkaffee.lda.lda.SimpleCounter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleCounterTest {
    @Test
    public void shouldFilterCountsLesserThan() throws Exception {
        SimpleCounter<String> counts = new SimpleCounter<>();
        for (int i = 0; i < 11; i++) counts.increaseByOne("foo");
        for (int i = 0; i < 10; i++) counts.increaseByOne("bar");
        for (int i = 0; i < 9; i++) counts.increaseByOne("baz");
        counts.filterCountsLessThan(10);
        assertThat(counts.getCount("foo"), is(11));
        assertThat(counts.getCount("bar"), is(10));
        assertThat(counts.getCount("baz"), is(0));
    }

}