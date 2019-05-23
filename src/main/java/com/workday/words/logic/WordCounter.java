package com.workday.words.logic;

import com.workday.words.exceptions.CounterException;
import com.workday.words.interfaces.ICounter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class WordCounter implements ICounter {
    /***
     * First creates a frequency map for the occurrences of the words. The key is the word, the value the number of occurrences
     * Second, creates the inverse map, with the frequency as key, and the list of words that have that frequency as value
     * @param words
     * @return
     * @throws CounterException
     */
    public Map<Long, List<String>> count(List<String> words) throws CounterException {

        try {
            var wordMap =
                    words.parallelStream()
                            //Creating map to count occurrences of the same word
                            .collect(groupingBy(Function.identity(), Collectors.counting()))
                            .entrySet().parallelStream()
                            //Creating second map, now the key is the number of hits, and the value the list of words that have that number of hits
                            .collect(groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
            return wordMap;
        } catch (Exception e){
            throw new CounterException(e);
        }
    }

}
