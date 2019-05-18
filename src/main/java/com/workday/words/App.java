package com.workday.words;

import com.workday.words.connections.QueryInformation;
import com.workday.words.processor.WordCount;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//TODO look for a better way to import this
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        //TODO DEPENDENCY INJECTION FRAMEWORK? CONFIG LOADING?

        //String testLoad = " to the Meadowlands Sports Complex in 1984. The Jets advanced to the playoffs for the first time in 1968 and went on to compete in Super Bowl III where they defeated the Baltimore Colts, becoming the first AFL team to defeat an NFL club in an AFL\\u2013NFL World Championship Game. Since 1968, the Jets have appeared in the playoffs 13 times, and in the AFC Championship Game four times, most recently losing to the Pittsburgh Steelers in 2010. However, the Jets have never returned to the Super Bowl, making them one of three NFL teams to win their lone Super Bowl appearance, along with the New Orleans Saints and Tampa Bay Buccaneers. Apart from the Cleveland Browns and Detroit Lions, who have never reached the Super Bowl (although both won NFL championships prior to 1966), the Jets' drought is the longest among current NFL franchises.\\n</p><p>The team's training facility, Atlantic Health Jets Training Center, which opened in 2008, is located in Florham Park. The team";
        String testLoad = "tio tio abuelo tio hijo tio abuelo";
        String testLoad2 = "tio tio hijo perro";
        var processor = new WordCount();

        var partialBody = new ArrayBlockingQueue<String>(100);
        var queryInputStream = new QueryInformation(partialBody);
        String content = queryInputStream.getPageStream("21721040");


        //TODO split text while doing request as bytes stream
        var partialResult = processor.process(Arrays.asList(content.split(" ")));
        //partialResult.forEach((k, v) -> System.out.println("k:" + k + " v:" + v));
        /*var partialResult1 = processor.process(Arrays.asList(testLoad2.split(" ")));

        Map<String, Long> []preProcessedResults = new Map[]{partialResult, partialResult1};

        //Merging results of several processors
        var mergedResult = Arrays.asList(preProcessedResults).stream().flatMap(m -> m.entrySet().stream()).collect(groupingBy(Map.Entry::getKey, summingLong(Map.Entry::getValue)));
        mergedResult.forEach((k,v) -> System.out.println("k:" + k + " \tv:" + v));*/
    }
}
