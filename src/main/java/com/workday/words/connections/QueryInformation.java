package com.workday.words.connections;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.inject.Inject;
import com.workday.words.exceptions.QueryException;
import com.workday.words.exceptions.SplitException;
import com.workday.words.interfaces.IQueryInformation;
import com.workday.words.interfaces.ISplitter;
import com.workday.words.logic.WordSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

public class QueryInformation implements IQueryInformation {

    //TODO use uri builder
    private String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&pageids=";
    private String params = "&explaintext&format=json";

    //TODO inject
    private ISplitter splitter = new WordSplitter();

    public void getPageStream(String pageId, BlockingQueue<String> outQueue) throws QueryException, SplitException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + pageId + params))
                .GET()
                .header("User-Agent", "Java 11")
                .build();

        try {
            //TODO make async if needed
            var response = client.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                this.splitter.split(response.body(), outQueue, pageId);
                /*try( var scanner = new Scanner(response.body())){
                    //Skipping twice just to be sure we are on the right label
                    scanner.skip(Pattern.compile(".*" + pageId));
                    scanner.skip(".*extract");


                    //TODO scan TITLE
                    String breakRegex = "\\.|:|\\n|-";
                    String matchRegex = "\\w*["+ breakRegex + "]\\w*";

                    while(scanner.hasNext()) {

                        String word = scanner.next();

                        if(word.matches(matchRegex)) {
                            var words = word.split(breakRegex);
                            for(var token : words){
                                outQueue.add(token);
                            }
                        } else {
                            outQueue.add(word);
                        }
                        System.out.println("WWord:" + word);
                    }

                } catch (Exception e){
                    throw new QueryException("Error traversing stream", e);
                }*/

            } else {
                throw new QueryException("Bad request:" + response.statusCode());
            }
        } catch (IOException e) {

            throw new QueryException("IO error performing request", e);
        } catch (InterruptedException e) {
            throw new QueryException("Interrupted request", e);
        }
    }

}
