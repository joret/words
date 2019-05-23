package com.workday.words.logic;

import com.workday.words.exceptions.SplitException;
import com.workday.words.interfaces.ISplitter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class WordSplitter implements ISplitter {
    @Override
    /***
     * Searches for the Title and Page Content in the inputstream, allowing to search in big texts without
     * deserializing the json object to a class
     */
    public List<String> split(InputStream stream, String pageId, List<String> returnTitle) throws SplitException{
        try( var scanner = new Scanner(stream, "UTF-8")) {
            List<String> splitText = new ArrayList<String>();

            //Skipping to pageId
            scanner.skip(".*" + pageId);
            scanner.useDelimiter(":|,");

            String breakRegex = "[\\.|:|\\n|-]";

            String previousTag = "";
            final String title = ".*\"title\".*";
            final String extract = ".*\"extract\".*";
            while (scanner.hasNext()) {
                String token = scanner.next();
                if(previousTag.equals(title)) {
                    returnTitle.add(token.replaceAll("\"", ""));

                    previousTag = "";
                    scanner.useDelimiter(":");
                }

                if(previousTag.equals(extract)) {
                    splitText.addAll(extractWords(breakRegex, token));
                }

                if(token != null && token.length() > 0)
                    //If we are in the title token,we break by ':' or ','
                    if(token.matches(title)) {
                        previousTag = title;
                        scanner.useDelimiter(",");
                    }
                    //- Else if we are in the Page content (extract tag) we break by whitespace
                    else if (token.matches(extract)) {
                        previousTag = extract;
                        scanner.useDelimiter("\\p{javaWhitespace}+");
                    }
            }
            return splitText;
        } catch (Exception e){
            throw new SplitException(e);
        }


    }

    private List<String> extractWords(String breakRegex, String word) {
        var wordsReturn = new ArrayList<String>();
        word = word.replaceAll("\\\\n", "-");
        if (word.contains(".") || word.contains("\n") || word.contains(":") || word.contains("-")) {
            var words = word.split(breakRegex);
            for (var token : words) {

                wordsReturn.add(token);
            }
        } else {
            wordsReturn.add(word);
        }

        return wordsReturn;
    }
}
