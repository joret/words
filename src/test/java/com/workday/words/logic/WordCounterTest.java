package com.workday.words.logic;

import com.workday.words.interfaces.ICounter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WordCounterTest {
    ICounter counter;

    @Before
    public void setUp() throws Exception {
        counter = new WordCounter();
    }

    @Test
    public void countWordsOk() throws Exception {
        var expected = Map.of(3l, Arrays.asList("aaaa"), 1l, Arrays.asList("cccc", "bbbb"));
        var words = Arrays.asList("aaaa", "bbbb", "aaaa", "cccc", "aaaa");
        var counted = counter.count(words);

        assertThat(counted, is(expected));

    }
}