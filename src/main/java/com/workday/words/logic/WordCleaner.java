package com.workday.words.logic;

import java.util.List;
import java.util.stream.Collectors;

public class WordCleaner {

    public List<String> cleanAndFilter(List<String> words){
        if(words == null)
            return null;

        //Filtering bad data, like empty strings, empty spaces on words and grouping by word to use counting collector
        /*TODO clean html tags, decode url strings, remove special char like dot
         Words valid only with alphabet chars*/
        var cleanWords = words.parallelStream()
                .map(w -> w.trim().replaceAll("[\\.=,]$|^\\.|^\\,|\\n|\\r", ""))
                .filter(w -> w.length() >= 4).collect(Collectors.toList());

        return cleanWords;
    }
}
