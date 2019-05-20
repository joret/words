package com.workday.words.interfaces;

import com.workday.words.exceptions.CounterException;

import java.util.List;
import java.util.Map;

public interface ICounter {
    Map<Long, List<String>> count(List<String> words) throws CounterException;
}
