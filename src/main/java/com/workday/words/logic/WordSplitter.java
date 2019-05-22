package com.workday.words.logic;

import com.workday.words.exceptions.SplitException;
import com.workday.words.interfaces.ISplitter;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class WordSplitter implements ISplitter {
    @Override
    public void split(InputStream stream, BlockingQueue<String> outQueue, String pageId) throws SplitException{
        try( var scanner = new Scanner(stream)) {
            //Skipping to pageId
            scanner.skip(".*" + pageId);
            scanner.useDelimiter(":|,");

            String breakRegex = "[\\.|:|\\n|-]";

            String previousTag = "";
            final String title = ".*\"title\".*";
            final String extract = ".*\"extract\".*";
            while (scanner.hasNext()) {
                String token = scanner.next();
                if(previousTag.equals(title))
                    System.out.println("title:" + token);

                if(previousTag.equals(extract)) {
                    extractWords(outQueue, breakRegex, token);
                }

                if(token != null)
                    //If we are in the title token,we break by ':' or ',' - Else we break by whitespace
                    if(token.matches(title)) {
                        previousTag = title;
                        scanner.useDelimiter(":|,");
                    }
                    else if (token.matches(extract)) {
                        previousTag = extract;
                        scanner.useDelimiter("\\p{javaWhitespace}+");
                    }
            }
        } catch (Exception e){
            throw new SplitException(e);
        }
    }

    private void extractWords(BlockingQueue<String> outQueue, String breakRegex, String word) {
        word = word.replaceAll("\\\\n", "-");
        if (word.contains(".") || word.contains("\n") || word.contains(":") || word.contains("-")) {
            var words = word.split(breakRegex);
            for (var token : words) {

                outQueue.add(token);
                System.out.println("WWord:" + token);
            }
        } else {
            outQueue.add(word);

            System.out.println("WWord:" + word);
        }
    }
}
