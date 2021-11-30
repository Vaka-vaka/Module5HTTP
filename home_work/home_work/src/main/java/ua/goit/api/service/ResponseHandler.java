package ua.goit.api.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ResponseHandler implements org.apache.http.client.ResponseHandler<String>{
    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        int code = response.getStatusLine().getStatusCode();
        if (code == 200) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + code);
        }
    }
}
