package com.workday.words.interfaces;

import com.workday.words.exceptions.QueryException;

import java.util.concurrent.BlockingQueue;

public interface IQueryInformation {
    void getPageStream(String pageId, BlockingQueue<String> outQueue) throws QueryException;
}
