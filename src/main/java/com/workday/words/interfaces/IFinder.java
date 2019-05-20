package com.workday.words.interfaces;

import com.workday.words.exceptions.FindException;

import java.util.List;
import java.util.Map;

public interface IFinder {
    Map<Long, List<String>> findHits(Map<Long, List<String>> wordMap, int topHitsToFind) throws FindException;
}
