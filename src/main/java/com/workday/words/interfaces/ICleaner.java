package com.workday.words.interfaces;

import com.workday.words.exceptions.CleanException;

import java.util.List;

public interface ICleaner {
    List<String> cleanAndFilter(List<String> inputQueue) throws CleanException;
}
