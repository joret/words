package com.workday.words.logic;

import com.workday.words.exceptions.FindException;
import com.workday.words.interfaces.IFinder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TopWordsFinderTest {
    private IFinder sut;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        sut = new TopWordsFinder();
    }

    @Test
    public void findHits() throws Exception{
        var wordsMap = Map.of(3l, Arrays.asList("aaaa", "bbbb", "cccc"), 5l ,Arrays.asList("dddd"), 1l, Arrays.asList("eeee", "ffff" ));

        var hitsFound = sut.findHits(wordsMap, 1);
        assertEquals(1, hitsFound.size());
        assertThat(wordsMap.get(5l), is(Arrays.asList("dddd")));
    }

    @Test
    public void findHitsEmptyInput() throws Exception{
        var wordsMap = new HashMap<Long, List<String>>();

        var hitsFound = sut.findHits(wordsMap, 1);
        assertEquals(0, hitsFound.size());
    }

    @Test
    public void findHitsNullInput() throws Exception{
        exception.expect(FindException.class);
        sut.findHits(null, 1);
    }
}