package org.foodbutler;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {
    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public void close() throws IOException {
        httpClient.close();
    }

    public void sendGet() throws Exception {

        HttpGet request = new HttpGet("https://us1.locationiq.com/v1/search.php?key=2951728dd8363f&q=empire%20state%20building&format=json");

        // add request headers
//        request.addHeader("custom-key", "mkyong");
//        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            //Get HttpResponse Status
            //Logger.info(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            
            Logger.info(result);
        	JSONArray json = new JSONArray(result);
        	JSONObject e = json.getJSONObject(1);
        	String lat = (String) e.get("lat");
            Logger.info(lat);

        }

    }

    private void sendPost() throws Exception {

        HttpPost post = new HttpPost("https://httpbin.org/post");

        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("username", "abc"));
        urlParameters.add(new BasicNameValuePair("password", "123"));
        urlParameters.add(new BasicNameValuePair("custom", "secret"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            //System.out.println(EntityUtils.toString(response.getEntity()));
        }

    }
}
