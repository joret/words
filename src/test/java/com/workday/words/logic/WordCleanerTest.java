package com.workday.words.logic;

import com.workday.words.interfaces.ICleaner;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WordCleanerTest {
    private ICleaner wordCleaner;

    @Before
    public void setUp() throws Exception {
        wordCleaner = new WordCleaner();
    }

    @Test
    public void cleanWordsLessThan4Chars() throws Exception{
        var words = Arrays.asList("aaa.", "b ", " c");
        var cleaned = wordCleaner.cleanAndFilter(words);

        assertThat(cleaned, is(new ArrayList()));
    }

    @Test
    public void cleanAndFilter() throws Exception{
        var expected = Arrays.asList("aaaa", "bbbb", "cccc");
        var words = Arrays.asList("aaaa.", "bbbb ", " cccc");
        var cleaned = wordCleaner.cleanAndFilter(words);

        assertThat(cleaned, is(expected));
    }


    @Test
    public void normalizeToLowerCase() throws Exception{
        var expected = Arrays.asList("aaaa", "bbbb", "aaaa");
        var words = Arrays.asList("aaaa.", "bbbb ", " AaAa");
        var cleaned = wordCleaner.cleanAndFilter(words);

        assertThat(cleaned, is(expected));
    }
}