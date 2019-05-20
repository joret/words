package com.workday.words.connections;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.workday.words.exceptions.QueryException;
import com.workday.words.interfaces.IQueryInformation;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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


                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(response.body());
                return readWikipedia(parser, pageId);
            } else {
                throw new QueryException("Bad request:" + response.statusCode());
            }
        } catch (IOException e) {
            new QueryException("IO error", e);
        } catch (InterruptedException e) {
            new QueryException("Interrupted reading stream", e);
        }

        return  null;
    }

    private String readWikipedia(JsonParser jp, String pageId){
        try {


            //TODO put condition to end loop
            while (true) {

                if(jp.nextToken() == JsonToken.START_OBJECT){
                    jp.nextToken();
                    String fieldName = jp.getCurrentName();


                        if (fieldName != null && fieldName.equals(pageId)){
                            break;
                        }

                }
            }

            while(true){

                    //TODO test with text size GB
                    String current =  jp.nextTextValue();

                    if("extract".equals(jp.currentName())){

                        return current;
                    }


                    if( jp.nextToken() == JsonToken.END_OBJECT){
                        break;
                    }
            }
            jp.close(); // important to close both parser and underlying File reader

        }catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }
        return null;
    }
}
