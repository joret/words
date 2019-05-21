package com.workday.words.interfaces;

import com.workday.words.exceptions.CleanException;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface ICleaner {
    List<String> cleanAndFilter(BlockingQueue<String> inputQueue) throws CleanException;
}
