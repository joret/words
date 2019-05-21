package com.workday.words.logic;

import com.workday.words.interfaces.ISplitter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class WordSplitterTest {

    private ISplitter sut;

    @Before
    public void init(){
        sut = new WordSplitter();
    }

    @Test
    public void splitTest() throws Exception{

        String string = "{\"query\":{\"pages\":{\"1\":{\"pageid\":1,\"ns\":0,\"title\":\"Stack Overflow\",\"extract\":\"harshly.\\nStack\"}}}}";

        //use ByteArrayInputStream to get the bytes of the String and convert them to InputStream.
        InputStream inputStream = new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8")));
        var queue = new ArrayBlockingQueue<String>(1000);
        sut.split(inputStream, queue, "1");
        System.out.println("Test");

        //TODO assert
    }
}