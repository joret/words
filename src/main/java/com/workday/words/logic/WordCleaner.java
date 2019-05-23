package com.workday.words.logic;

import com.workday.words.exceptions.CleanException;
import com.workday.words.interfaces.ICleaner;

import java.util.List;
import java.util.stream.Collectors;

/***
 * Cleans and filters undesired chars, minimum of 4 letters, normalizes to lowercase
 */
public class WordCleaner implements ICleaner {

    public List<String> cleanAndFilter(List<String> words) throws CleanException{
        try {
            //Filtering bad data, like empty strings, empty spaces on words and grouping by word to use counting collector
            //Normalizing to lowercase
            String charsToDiscard = "[^a-zA-Z]";
            var cleanWords = words.parallelStream()
                    .map(w -> w.trim().replaceAll(charsToDiscard, "").toLowerCase())
                    .filter(w -> w.length() >= 4).collect(Collectors.toList());
            return cleanWords;
        } catch (Exception e){
            throw new CleanException(e);
        }
    }
}
