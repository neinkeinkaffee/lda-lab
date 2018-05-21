package org.tw.neinkeinkaffee.lda.lda;

import org.junit.Test;
import org.tw.neinkeinkaffee.lda.lda.PairCounter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PairCounterTest {
    @Test
    public void shouldFilterCountsLessThan() throws Exception {
        PairCounter<String, Integer> counts = new PairCounter<>();
        for (int i = 0; i < 11; i++) counts.increaseByOne("foo", 1);
        for (int i = 0; i < 10; i++) counts.increaseByOne("bar", 2);
        for (int i = 0; i < 9; i++) counts.increaseByOne("baz", 3);
        counts.filterCountsLessThan(10);
        assertThat(counts.getCount("foo", 1), is(11));
        assertThat(counts.getCount("bar", 2), is(10));
        assertThat(counts.getCount("baz", 3), is(0));
    }
}