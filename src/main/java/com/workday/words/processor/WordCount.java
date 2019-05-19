package com.workday.words.processor;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


public class WordCount{
    public Map<String, Long> process(List<String> words){
        if(words == null)
            return null;

        //Filtering bad data, like empty strings, empty spaces on words and grouping by word to use counting collector
        //TODO clean html tags, decode url strings, remove special char like dot
        //Words valid only with alphabet chars
        var wordMap = words.parallelStream()
                .map(w -> w.trim().replaceAll("[\\.=,]$|^\\.|^\\,|\\n|\\r", ""))
                .filter(w -> w.length() >= 4)
                //map(word -> new AbstractMap.SimpleEntry<>(word.trim().replaceAll("[\\.=,]$|^\\.|^\\,|\\n|\\r", ""), 1l) {}).
                    //    map(word -> )
                //collect(groupingBy(Map.Entry::getKey, counting()));

                .collect(groupingBy(Function.identity(), Collectors.counting()));


                //wordMap.forEach((k , v) -> System.out.println("*****k:" +k + " v:" + v));

        //TODO treat same value hits
        //var treeMap =
                wordMap.entrySet().stream()
                .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(20)
                .map(e -> new AbstractMap.SimpleEntry(e.getValue(), e.getKey()))
                .collect(groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .forEach((e,v ) -> System.out.println("/*/**/*/*/k:" +e + " v:" + v));
                //.collect(Collectors.toMap())
                /*.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(),
                (v1,v2) ->{ throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));},
                TreeMap::new));*/


       /* for(var e : treeMap.keySet()){
            System.out.println("------" + treeMap.get(e));
        }*/
                //c;
        return wordMap;
    }
}
