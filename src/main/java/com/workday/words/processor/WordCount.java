package com.workday.words.processor;

import java.util.*;


//TODO look for a better way to import this
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordCount{
    public Map<String, Long> process(List<String> words){
        if(words == null)
            return null;

        //Filtering bad data, like empty strings, empty spaces on words and grouping by word to use counting collector
        //TODO clean html tags, decode url strings, remove special char like dot
        var wordMap = words.parallelStream().filter(w -> w.length() >= 4).
                map(word -> new AbstractMap.SimpleEntry<>(word.trim().replaceAll("[\\.=,]$|^\\.|^\\,|\\n|\\r", ""), 1l) {}).
                collect(groupingBy(Map.Entry::getKey, counting()));

        // String lk = wordMap.lastKey();
        wordMap.entrySet().stream().sorted(comparingByValue()).skip(wordMap.entrySet().size() - 5).forEach(System.out::println);
        return wordMap;
    }
}
