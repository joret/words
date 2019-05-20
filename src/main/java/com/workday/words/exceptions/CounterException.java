package com.workday.words.exceptions;

public class CounterException extends Exception {
    public CounterException(Exception e) {
        super(e);
    }
}
