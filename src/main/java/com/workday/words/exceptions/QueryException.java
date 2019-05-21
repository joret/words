package com.workday.words.exceptions;

public class QueryException extends Exception {
    public QueryException(String s) {
        super(s);
    }

    public QueryException(String error, Exception e) {
        super(error, e);
    }
}
