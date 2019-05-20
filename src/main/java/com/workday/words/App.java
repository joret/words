package com.workday.words;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.workday.words.connections.QueryInformation;
import com.workday.words.exceptions.CleanException;
import com.workday.words.exceptions.CounterException;
import com.workday.words.exceptions.FindException;
import com.workday.words.exceptions.QueryException;
import com.workday.words.interfaces.ICleaner;
import com.workday.words.interfaces.ICounter;
import com.workday.words.interfaces.IFinder;
import com.workday.words.interfaces.IQueryInformation;
import com.workday.words.logic.TopWordsFinder;
import com.workday.words.logic.WordCleaner;
import com.workday.words.logic.WordCounter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class App extends AbstractModule
{
    ICounter counter;
    ICleaner cleaner;
    IFinder topFinder;
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
            System.out.println("System failure:" + e.getMessage());
            e.printStackTrace();
        }

    }

    public Map<Long, List<String>> run (String[] args ) throws CleanException, QueryException, CounterException, FindException {
        //TODO perform the operations using lambda too
        //TODO CONFIG LOADING? logging framework?
        //TODO rebuild QueryInformation to consume partial parts of string

        String content = this.queryInformation.getPageStream(args[0]);

        var cleanData = this.cleaner.cleanAndFilter(Arrays.asList(content.split(" ")));
        var hitsAndData = this.counter.count(cleanData);
        var topHitsData = this.topFinder.findHits(hitsAndData, Integer.valueOf(args[1]));

        return topHitsData;
    }

    @Override
    /***
     * Configure dependency injection
     */
    protected void configure(){
        bind(ICounter.class).to(WordCounter.class);
        bind(IFinder.class).to(TopWordsFinder.class);
        bind(ICleaner.class).to(WordCleaner.class);
        bind(IQueryInformation.class).to(QueryInformation.class);
    }

    @Inject
    public void App(ICounter counter, ICleaner cleaner, IFinder finder, IQueryInformation queryInformation){
        this.counter = counter;
        this.cleaner = cleaner;
        this.topFinder = finder;
        this.queryInformation = queryInformation;
    }
}



