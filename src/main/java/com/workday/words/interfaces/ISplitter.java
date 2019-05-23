package com.workday.words.interfaces;

import com.workday.words.exceptions.SplitException;

import java.io.InputStream;
import java.util.List;

public interface ISplitter {
    List<String> split(InputStream stream, String pageId, List<String> returnTitle) throws SplitException;
}
