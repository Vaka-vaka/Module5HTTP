package ua.goit.service;

import java.io.IOException;
import java.net.URL;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

public class HttpApiService {

  private static HttpRequestBase createBaseRequest(final URL url, final HttpMethod method)
      throws HttpException {
    if (method == HttpMethod.GET) {
      return new HttpGet(url.toString());
    }
    if (method == HttpMethod.POST) {
      return new HttpPost(url.toString());
    }
    if (method == HttpMethod.PUT) {
      return new HttpPut(url.toString());
    }
    if (method == HttpMethod.DELETE) {
      return new HttpDelete(url.toString());
    }
    throw new HttpException("unknown HTTP method");
  }

  public static String getRequest(HttpRequestBase httpRequestBase) throws IOException {
    String result = "";
    try (CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpRequestBase)) {
      result = EntityUtils.toString(response.getEntity());
    }
    return result;
  }

  public static HttpRequestBase methodOfHttp(URL url, HttpMethod method) throws HttpException {
    HttpRequestBase httpRequestBase = createBaseRequest(url, method);
    httpRequestBase.addHeader("Content-Type", "application/json");
    httpRequestBase.addHeader("Accept", "application/json");
    return httpRequestBase;
  }

  public static HttpRequestBase methodOfHttpWithDataContentType(URL url, HttpMethod method) throws HttpException {
    HttpRequestBase httpRequestBase = createBaseRequest(url, method);
    httpRequestBase.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    httpRequestBase.addHeader("Accept", "application/json");
    return httpRequestBase;
  }
  public static <E> E nvl(E expr1, E expr2) {
    return (null != expr1) ? expr1 : expr2;
  }

}