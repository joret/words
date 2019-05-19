package com.workday.words.logic;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class TopWordsFinder {
    public Map<Long, List<String>> findHits(Map<Long, List<String>> wordMap, int topHitsToFind){
        var sortedMap = wordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).limit(topHitsToFind)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        return sortedMap;
    }
}
