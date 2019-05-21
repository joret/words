package com.workday.words.logic;

import com.workday.words.exceptions.SplitException;
import com.workday.words.interfaces.ISplitter;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

public class WordSplitter implements ISplitter {
    @Override
    public void split(InputStream stream, BlockingQueue<String> outQueue, String pageId) throws SplitException{
        try( var scanner = new Scanner(stream)) {
            //Skipping twice just to be sure we are on the right label
            scanner.skip(Pattern.compile(".*" + pageId));
            scanner.skip(".*extract");
            //TODO scan TITLE
            String breakRegex = "\\.|:|\\n|-";
            String matchRegex = "\\w*[" + breakRegex + "]\\w*";

            while (scanner.hasNext()) {

                String word = scanner.next();

                if (word.matches(matchRegex)) {
                    var words = word.split(breakRegex);
                    for (var token : words) {
                        outQueue.add(token);
                    }
                } else {
                    outQueue.add(word);
                }
                System.out.println("WWord:" + word);
            }
        } catch (Exception e){
            throw new SplitException(e);
        }
    }
}
