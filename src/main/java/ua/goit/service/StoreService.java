package ua.goit.service;

import io.netty.handler.codec.http.HttpMethod;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.http.HttpException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

public class StoreService {

  private static final String URL = "https://petstore.swagger.io/v2/store";
  private static HttpGet httpGet;
  private static final int ID = 0;

  private static String getBody(Integer id, Integer petId, Integer quantity) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    format.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date date = new Date(System.currentTimeMillis());
    return "{\n"
        + "  \"id\": " + id + ",\n"
        + "  \"petId\": " + petId + ",\n"
        + "  \"quantity\": " + quantity + ",\n"
        + "  \"shipDate\": \"" + format.format(date)  + "\",\n"
        + "  \"status\": \"placed\",\n"
        + "  \"complete\": true\n"
        + "}";
  }

  public static String inventory() throws IOException, HttpException {
    httpGet = (HttpGet) HttpApiService.methodOfHttp(new URL(URL + "/inventory"), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }

  public static String orderPet(Integer id, Integer petId, Integer quantity)
      throws IOException, HttpException {
    HttpPost httpPost = (HttpPost) HttpApiService.methodOfHttp(new URL(URL + "/order"),
        HttpMethod.POST);
    String json = getBody(id, petId, quantity);
    httpPost.setEntity(new StringEntity(json));
    return HttpApiService.getRequest(httpPost);
  }

  public static String orderPet() throws IOException, HttpException {
    return orderPet(ID, ID, ID);
  }

  public static String findOrderById(Integer id) throws IOException, HttpException {
    httpGet = (HttpGet) HttpApiService.methodOfHttp(new URL(URL + "/order/" + id), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }

  public static String deleteOrderById(Integer id) throws IOException, HttpException {
    HttpDelete httpDelete = (HttpDelete) HttpApiService
        .methodOfHttp(new URL(URL + "/order/" + id), HttpMethod.DELETE);
    return HttpApiService.getRequest(httpDelete);
  }
}