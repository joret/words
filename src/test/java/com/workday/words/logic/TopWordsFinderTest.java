package com.workday.words.logic;

import com.workday.words.interfaces.IFinder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class TopWordsFinderTest {
    IFinder sut;

    @Before
    public void setUp() {
        sut = new TopWordsFinder();
    }

    @Test
    public void findHits() throws Exception{
        var wordsMap = new HashMap<Long, List<String>>();

        var hitsFound = sut.findHits(wordsMap, 1);
        assertEquals(1, hitsFound.size());

    }
}