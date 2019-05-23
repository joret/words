package com.workday.words.logic;

import com.workday.words.interfaces.ISplitter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WordSplitterTest {

    private ISplitter sut;

    @Before
    public void init(){
        sut = new WordSplitter();
    }

    @Test
    public void splitTest() throws Exception{
        var expected1 = "\"Hi";
        var expected2 = "Askbot\"";
        String string = "{\"query\":{\"pages\":{\"1\":{\"pageid\":1,\"ns\":0,\"title\":\"Stack Overflow\",\"extract\":\"Hi Askbot\"";

        InputStream inputStream = new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8")));

        var title = new ArrayList<String> ();
        var words = sut.split(inputStream,  "1", title);



        assertThat(words.get(1), is(expected1));


        assertThat(words.get(2), is(expected2));
    }
}