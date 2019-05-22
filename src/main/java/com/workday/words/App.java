package com.workday.words;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.workday.words.connections.QueryInformation;
import com.workday.words.exceptions.*;
import com.workday.words.interfaces.*;
import com.workday.words.logic.TopWordsFinder;
import com.workday.words.logic.WordCleaner;
import com.workday.words.logic.WordCounter;
import com.workday.words.logic.WordSplitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 *
 */
public class App extends AbstractModule
{
    //Instantiating logic objects with new, but they can be injected if needed later with new implementations
    ICounter counter = new WordCounter();
    ICleaner cleaner = new WordCleaner();
    IFinder topFinder = new TopWordsFinder();

    //Injecting object that connect to external resource
    IQueryInformation queryInformation;

    public static void main( String[] args ) {

        try {
            if (args.length < 2) {
                System.out.println("Requires 2 parameters: 1) PageId 2) Number of hits required");
                return;
            }

            //Dependency injector
            Injector injector = Guice.createInjector(new App());
            var app = injector.getInstance(App.class);

            //Core logic
            var topHits = app.run(args);

            if (topHits != null) {
                System.out.println("Top hits");
                topHits.forEach((k, v) -> System.out.println("k:" + k + " v:" + v));
            }

        } catch (Exception e) {
            //Here we will now exactly where the system had an issue, as each processing unit has it's
            //own exception type in the exception package
            System.out.println("System failure:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public Map<Long, List<String>> run (String[] args ) throws CleanException, QueryException,
            CounterException, FindException, SplitException {
        //TODO put cancellation token to shutdown gracefully
        //TODO see if the arrayblocking is the best and tune capacity properly
        BlockingQueue<String> wordsQueue = new ArrayBlockingQueue<>(100000);
        this.queryInformation.getPageStream(args[0], wordsQueue);

        if(wordsQueue != null && wordsQueue.size() > 0) {

            var words = new ArrayList<String>();

            //TODO use javaRX observable collection
            for(var word : wordsQueue) {
                words.add(word);
            }
            var cleanData = this.cleaner.cleanAndFilter(words);

            //TODO REMOVE log
            for (var cleanWord : cleanData) {
                System.out.println("CLEAN: " + cleanWord);
            }
            var hitsAndData = this.counter.count(cleanData);
            var topHitsData = this.topFinder.findHits(hitsAndData, Integer.valueOf(args[1]));

            return topHitsData;
        }

        return null;
    }

    @Override
    /***
     * Configure dependency injection
     * Injecting only the connection class to retrieve the wiki page text. Can inject more later, but for now the query
     * to wikipedia external resource is the only object to inject when testing
     */
    protected void configure(){
        bind(IQueryInformation.class).to(QueryInformation.class);
        bind(ISplitter.class).to(WordSplitter.class);
    }

    @Inject
    public void App(IQueryInformation queryInformation){
        this.queryInformation = queryInformation;
    }
}