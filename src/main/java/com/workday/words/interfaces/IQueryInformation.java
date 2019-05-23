package com.workday.words.interfaces;

import com.workday.words.exceptions.QueryException;
import com.workday.words.exceptions.SplitException;

import java.util.List;

public interface IQueryInformation {
    List<String> getPageStream(String pageId, List<String> returnTitle) throws QueryException, SplitException;
}
