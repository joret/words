package com.workday.words.logic;

import com.workday.words.exceptions.CleanException;
import com.workday.words.interfaces.ICleaner;

import java.util.List;
import java.util.stream.Collectors;

public class WordCleaner implements ICleaner {

    public List<String> cleanAndFilter(List<String> words) throws CleanException{
        try {
            if (words == null)
                return null;

            //Filtering bad data, like empty strings, empty spaces on words and grouping by word to use counting collector
            //Normalizing to lowercase
        /*TODO clean html tags, decode url strings, remove special char like dot . Words valid only with alphabet chars*/
            var cleanWords = words.parallelStream()
                    .map(w -> w.trim().replaceAll("[\\.=,]$|^\\.|^\\,|\\n|\\r", "").toLowerCase())
                    .filter(w -> w.length() >= 4).collect(Collectors.toList());
            return cleanWords;
        } catch (Exception e){
            throw new CleanException(e);
        }
    }
}
