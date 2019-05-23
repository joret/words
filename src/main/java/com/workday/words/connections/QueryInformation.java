package com.workday.words.connections;

import com.workday.words.exceptions.QueryException;
import com.workday.words.exceptions.SplitException;
import com.workday.words.interfaces.IQueryInformation;
import com.workday.words.interfaces.ISplitter;
import com.workday.words.logic.WordSplitter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class QueryInformation implements IQueryInformation {

    private String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&pageids=";
    private String params = "&explaintext&format=json";

    //Can be injected later if required
    private ISplitter splitter = new WordSplitter();

    public List<String> getPageStream(String pageId, List<String> returnTitle) throws QueryException, SplitException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + pageId + params))
                .GET()
                .header("User-Agent", "Java 11")
                .header("Content-Type", "application/json")
                .header("charset", "UTF-8")
                .build();

        try {
            var response = client.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return this.splitter.split(response.body(), pageId, returnTitle);
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
