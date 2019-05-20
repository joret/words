package com.workday.words.interfaces;

import com.workday.words.exceptions.QueryException;

public interface IQueryInformation {
    String getPageStream(String pageId) throws QueryException;
}
