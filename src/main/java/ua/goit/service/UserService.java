package ua.goit.service;

import io.netty.handler.codec.http.HttpMethod;
import java.io.IOException;
import java.net.URL;
import org.apache.http.HttpException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

public class UserService {

  private static final String URL = "https://petstore.swagger.io/v2/user";
  private static final int ID = 0;
  private static final String NAME = "string";
  private static HttpPost httpPost;
  private static HttpGet httpGet;

  public static String getBody(Integer id, String username, String firstName, String lastName,
      String email, String password, String phone, Integer status) {
    return "{\n"
        + "    \"id\": " + id + ",\n"
        + "    \"username\": \"" + username + "\",\n"
        + "    \"firstName\": \"" + firstName + "\",\n"
        + "    \"lastName\": \"" + lastName + "\",\n"
        + "    \"email\": \"" + email + "\",\n"
        + "    \"password\": \"" + password + "\",\n"
        + "    \"phone\": \"" + phone + "\",\n"
        + "    \"userStatus\": " + status + "\n"
        + "}\n";
  }

  public static  String createUser(Integer id, String userName, String firstName, String lastName,
      String email, String password, String phone, Integer status) throws IOException, HttpException {
    httpPost = (HttpPost) HttpApiService
        .methodOfHttp(new URL(URL), HttpMethod.POST);
    String json = getBody(id, userName, firstName, lastName, email, password, phone, status);
    httpPost.setEntity(new StringEntity(json));
    return HttpApiService.getRequest(httpPost);
  }

  public static  String createUser() throws HttpException, IOException {
    return createUser(ID, NAME, NAME, NAME, NAME, NAME, NAME, ID);
  }

  public static String getUserByName(String userName) throws IOException, HttpException {
    httpGet = (HttpGet) HttpApiService
        .methodOfHttp(new URL(URL + "/" + userName), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }

  public static String updateUser(Integer id, String userName, String firstName, String lastName,
      String email, String password, String phone, Integer status) throws IOException, HttpException {
    httpPost = (HttpPost) HttpApiService
        .methodOfHttp(new URL(URL + "/" + userName), HttpMethod.POST);
    String json = getBody(id, userName, firstName, lastName, email, password, phone, status);
    httpPost.setEntity(new StringEntity(json));
    return HttpApiService.getRequest(httpPost);
  }

  public static String updateUser() throws IOException, HttpException {
    return updateUser(ID, NAME, NAME, NAME, NAME, NAME, NAME, ID);
  }

  public static String deleteUser(String userName) throws IOException, HttpException {
    HttpDelete httpDelete = (HttpDelete) HttpApiService
        .methodOfHttp(new URL(URL + "/" + userName), HttpMethod.DELETE);
    return HttpApiService.getRequest(httpDelete);
  }

  public static String userLogin(String userName, String password)
      throws IOException, HttpException {
    httpGet = (HttpGet) HttpApiService
        .methodOfHttp(new URL(URL+ "/login?username=" + userName + "&password=" + password), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }

  public static String userLogout() throws IOException, HttpException {
    httpGet = (HttpGet) HttpApiService
        .methodOfHttp(new URL(URL+ "/logout"), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }
}