package ua.goit.api.service;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpHelper {
    private static final JSONConverter CONVERTER = new JSONConverter();

    public static<T> HttpRequest createRequest(String uri, String method, T entity){
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(CONVERTER.convertObjectToJSON(entity)))
                .build();
    }

    public static HttpRequest createRequest(String url, String method){
        switch (method) {
            case "DELETE" -> {
                return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .DELETE()
                        .build();
            }
            case "GET" -> {
                return HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();
            }
        }
        return null;
    }

 }
