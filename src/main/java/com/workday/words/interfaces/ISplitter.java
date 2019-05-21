package com.workday.words.interfaces;

import com.workday.words.exceptions.SplitException;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

public interface ISplitter {
    void split(InputStream stream, BlockingQueue<String> outQueue, String pageId) throws SplitException;
}
