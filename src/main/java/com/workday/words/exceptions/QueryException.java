package com.workday.words.exceptions;

public class QueryException extends Exception {
    public QueryException(String s) {
        super(s);
    }

    public QueryException(String io_error, Exception e) {
        super(io_error, e);
    }
}
