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

    public ArrayList<Coordinates> sendGet(String store, String zip) throws Exception {

        HttpGet request = new HttpGet("https://us1.locationiq.com/v1/search.php?key=2951728dd8363f&q="+store+"+"+zip+"&format=json");
        Logger.info(request);
        ArrayList<Double> distance = new ArrayList<Double>();
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            ArrayList<Coordinates> coords = new ArrayList<Coordinates>();
            Logger.info(result);
        	JSONArray json = new JSONArray(result);
        	for (int i = 0; i < json.length(); i++)
        	{
        		JSONObject e = json.getJSONObject(i);
        		String lat = (String) e.get("lat");
        		String lon = (String) e.get("lon");
            	Coordinates coordinate = new Coordinates(lat, lon);
            	coords.add(coordinate);
        	}
        	return coords;
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
