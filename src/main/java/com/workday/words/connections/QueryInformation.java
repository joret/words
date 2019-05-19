package com.workday.words.connections;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class QueryInformation {

    //TODO use uri builder
    private String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&pageids=";
    private String params = "&explaintext&format=json";

    public String getPageStream(String pageId){
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
                //System.out.println(response.body());
            } else {
                //TODO better error handling
                System.out.println("Something wrong there!");
            }
        } catch (IOException e) {
            //TODO better error handling
            System.out.println("Something wrong here11!" + e);
        } catch (InterruptedException e) {
            //TODO better error handling
            System.out.println("Something wrong here22!" + e);
        }

        return  null;
    }

    public String readWikipedia(JsonParser jp, String pageId){
        boolean foundContentTag = false;

        try {
            // Sanity check: verify that we got "Json Object":
            /*if (jp.nextToken() != JsonToken.START_OBJECT) {
                throw new IOException("Expected data to start with an Object");
            }*/

            //while (jp.nextToken() != JsonToken.END_OBJECT && !foundContentTag) {

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
