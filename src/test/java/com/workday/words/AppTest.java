package com.workday.words;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.workday.words.interfaces.ICleaner;
import com.workday.words.interfaces.ICounter;
import com.workday.words.interfaces.IFinder;
import com.workday.words.interfaces.IQueryInformation;
import com.workday.words.logic.TopWordsFinder;
import com.workday.words.logic.WordCleaner;
import com.workday.words.logic.WordCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test for simple App.
 */
public class AppTest 
{
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new App() {

            @Override
            protected void configure(){
                bind(IQueryInformation.class).to(MockQuery.class);
            }
        });
    }

    /**
     * App integration test
     */
    @Test
    public void AppTest() throws Exception
    {
        //TODO validate this test
        var expected = Map.of(3l, Arrays.asList("davenport", "arabian"));
        var app = injector.getInstance(App.class);
        //First parameter does not matter, as the page will be injected by the mock
        var topHits = app.run(new String[]{"WillBeInjected","1"});

        assertThat(topHits, is(expected));
    }
}
