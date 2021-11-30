package ua.goit.service;

import io.netty.handler.codec.http.HttpMethod;
import java.io.*;
import java.net.URL;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class PetService {

  private static final String URL = "https://petstore.swagger.io/v2/pet";
  private static final int ID = 0;
  private static final String NAME = "string";
  private static final String STATUS = "available";
  private static HttpPost httpPost;
  private static HttpGet httpGet;

  private static String getBody(Integer id, Integer categoryId, String categoryName, String petName,
                               Integer tagsId, String tagName, String status) {
    return "{\n"
        + "  \"id\": " + id + ",\n"
        + "  \"category\": {\n"
        + "    \"id\": " + categoryId + ",\n"
        + "    \"name\": \"" + categoryName + "\"\n"
        + "  },\n"
        + "  \"name\": \"" + petName + "\",\n"
        + "  \"photoUrls\": [\n"
        + "    \"string\"\n"
        + "  ],\n"
        + "  \"tags\": [\n"
        + "    {\n"
        + "      \"id\": " + tagsId + ",\n"
        + "      \"name\": \"" + tagName + "\"\n"
        + "    }\n"
        + "  ],\n"
        + "  \"status\": \"" + status + "\"\n" //available, pending, sold
        + "}";
  }

  public static String uploadPetImage(Integer id, String pathFileToPhoto, String additionalMetadata)
      throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    httpPost = new HttpPost(URL + "/" + id + "/uploadImage");
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.addTextBody("additionalMetadata", additionalMetadata, ContentType.TEXT_PLAIN);

    File f = new File(pathFileToPhoto);
    builder.addBinaryBody(
        "file",
        new FileInputStream(f),
        ContentType.APPLICATION_OCTET_STREAM,
        f.getName()
    );
    HttpEntity multipart = builder.build();
    httpPost.setEntity(multipart);
    CloseableHttpResponse response = httpClient.execute(httpPost);
    HttpEntity responseEntity = response.getEntity();
    return EntityUtils.toString(responseEntity);
  }

  public static String addPet(Integer id, Integer categoryId, String categoryName, String petName,
      Integer tagsId, String tagName, String status) throws Exception {
    httpPost = (HttpPost) HttpApiService.methodOfHttp(new URL(URL), HttpMethod.POST);
    String json = getBody(id, categoryId, categoryName, petName,  tagsId, tagName, status);
    httpPost.setEntity(new StringEntity(json));
    return HttpApiService.getRequest(httpPost);
  }

  public static String addPet() throws Exception {
    return addPet(ID, ID, NAME, NAME, ID, NAME, STATUS);
  }

  public static String updatePet(Integer id, Integer categoryId, String categoryName,
      String petName, Integer tagsId, String tagName, String status)
      throws IOException, HttpException {
    HttpPut httpPut = (HttpPut) HttpApiService.methodOfHttp(new URL(URL), HttpMethod.PUT);
    String json = getBody(id, categoryId, categoryName, petName, tagsId, tagName, status);
    httpPut.setEntity(new StringEntity(json));
    return HttpApiService.getRequest(httpPut);
  }

  public static String updatePet() throws IOException, HttpException {
    return updatePet(ID, ID, NAME, NAME, ID, NAME, STATUS);
  }

  public static String findPetByStatus(String status) throws IOException, HttpException { //available, pending, sold
    httpGet = (HttpGet) HttpApiService
        .methodOfHttp(new URL(URL+ "/findByStatus?status=" + status), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }

  public static String findPetById(Integer id) throws IOException, HttpException {
    httpGet = (HttpGet) HttpApiService
        .methodOfHttp(new URL(URL+ "/" + id), HttpMethod.GET);
    return HttpApiService.getRequest(httpGet);
  }

  public static String updatePetFromDAta(Integer id) throws IOException, HttpException {
    httpPost = (HttpPost) HttpApiService
        .methodOfHttpWithDataContentType(new URL(URL + "/" + id), HttpMethod.POST);
    List<NameValuePair> urlParameters = new ArrayList<>();
    urlParameters.add(new BasicNameValuePair("petId ", id.toString()));
    httpPost.setEntity(new StringEntity(urlParameters.toString()));
    return HttpApiService.getRequest(httpPost);
  }

  public static String deletePet(Integer id) throws IOException, HttpException {
    HttpDelete httpDelete = (HttpDelete) HttpApiService.methodOfHttp(new URL(URL + "/" + id), HttpMethod.DELETE);
    httpDelete.addHeader("api_key", "special-key");
    return HttpApiService.getRequest(httpDelete);
  }

}