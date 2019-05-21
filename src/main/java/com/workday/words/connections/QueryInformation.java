package com.workday.words.connections;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.workday.words.exceptions.QueryException;
import com.workday.words.interfaces.IQueryInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class QueryInformation implements IQueryInformation {

    //TODO use uri builder
    private String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&pageids=";
    private String params = "&explaintext&format=json";

    public String getPageStream(String pageId) throws QueryException{

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + pageId + params))
                .GET()
                .header("User-Agent", "Java 11")
                .build();

        try {
            //TODO make async
            var response = client.send(request,
                    HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {

                JsonFactory jFactory = new JsonFactory();

                try(JsonParser jParser = jFactory.createParser(response.body())){
                    if(jParser.nextToken() == JsonToken.START_OBJECT){
                        var token = jParser.nextToken();

                        String toFind = pageId;

                        //TODO put loop protection
                        lookForTag(toFind, token, jParser);
                        lookForTag("extract", token, jParser);
                        //String val = jParser.nextTextValue();
                        var inputStream = (InputStream)jParser.getInputSource();



                        /*try (var reader = new BufferedReader(new InputStreamReader(inputStream))){
                            var line = reader.re();
                            System.out.println("Line:" + line);
                        }*/
                        try(var scanner = new Scanner(inputStream)){
                            scanner.useDelimiter(" ");
                            String result = scanner.next();
                            while( result != null){
                                System.out.println("words:" + result);
                                result = scanner.next();
                            }

                        }

                    }
                } catch (Exception e){
                    throw new QueryException("Parsing json exception.", e);
                }

            } else {
                throw new QueryException("Bad request:" + response.statusCode());
            }
        } catch (IOException e) {

            throw new QueryException("IO error performing request", e);
        } catch (InterruptedException e) {
            throw new QueryException("Interrupted request", e);
        }

        return null;
    }


    private void lookForTag(String tag, JsonToken token, JsonParser jParser) throws IOException{
        while(token != null){
            String currentName = jParser.getCurrentName();
            if(token == JsonToken.FIELD_NAME && currentName.equals(tag)){

                return;

            }
            token = jParser.nextToken();
        }
    }

}
