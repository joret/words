package com.workday.words.processor;

import java.util.*;


//TODO look for a better way to import this
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordCount{
    public Map<String, Long> process(List<String> words){
        if(words == null)
            return null;

        //Filtering bad data, like empty strings, empty spaces on words and grouping by word to use counting collector
        //TODO clean html tags, decode url strings, remove special char like dot
        var wordMap = words.parallelStream().filter(w -> w.length() != 0).map(word -> new AbstractMap.SimpleEntry<>(word.trim(), 1)).
                collect(groupingBy(AbstractMap.SimpleEntry::getKey, counting()));

        return wordMap;
    }
}
