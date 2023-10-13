package com.clubforce.util;

import com.clubforce.framework.WebDriverManager;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.FileAssert.fail;

public class HttpConnectionUtil extends WebDriverManager {

    //logger
    private static final Logger logger = LogManager.getLogger();
    public static boolean ignoreInvalidCerts;


    public static Map<String, String> sendRequestToProxy(String endpoint, String method, String payload, String sessionCookie) {

        try {

            //set authentication
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            if (!sessionCookie.equals("")) {
                headers.put("Cookie", sessionCookie);
            }

            //make request
            switch (method) {
                case ("GET"):
                    return makeRequestWithHeaders(new HttpGet(envURL + "/apiproxy" + endpoint), headers);
                case ("POST"):
                    HttpPost postRequest = new HttpPost(envURL + "/apiproxy" + endpoint);
                    postRequest.setEntity(new StringEntity(payload));
                    return makeRequestWithHeaders(postRequest, headers);
                case ("PUT"):
                    HttpPut putRequest = new HttpPut(envURL + "/apiproxy" + endpoint);
                    putRequest.setEntity(new StringEntity(payload));
                    return makeRequestWithHeaders(putRequest, headers);
                case ("DELETE"):
                    return makeRequestWithHeaders(new HttpDelete(envURL + "/apiproxy" + endpoint), headers);
            }

        } catch (Exception e) {
            fail("Exception sending HttpURLConnection request! " + e.toString());
        }
        return new HashMap<>();
    }

    public static Map<String, String> sendSimpleGETRequest(String endpoint) {
        try {
            //set authentication
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("accept", "application/json");
            return makeRequestWithHeaders(new HttpGet(endpoint), headers);

        } catch (Exception e) {
            fail("Exception sending HttpURLConnection request! ", e);
        }
        return new HashMap<>();
    }

    public static Map<String, String> sendRequest(String endpoint, String method, String payload, String authType, String authToken) {
        try {
            //set authentication
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("accept", "application/json");

            if (!(authType.equals("") && authToken.equals(""))) {
                headers.put(authType, authToken);
            }

            //make request
            switch (method) {
                case ("GET"):
                    return makeRequestWithHeaders(new HttpGet(endpoint), headers);
                case ("POST"):
                    HttpPost postRequest = new HttpPost(endpoint);
                    postRequest.setEntity(new StringEntity(payload));
                    return makeRequestWithHeaders(postRequest,headers);
                case ("PUT"):
                    HttpPut putRequest = new HttpPut(endpoint);
                    putRequest.setEntity(new StringEntity(payload));
                    return makeRequestWithHeaders(putRequest, headers);
                case ("DELETE"):
                    return makeRequestWithHeaders(new HttpDelete(endpoint), headers);
            }


        } catch (Exception e) {
            fail("Exception sending HttpURLConnection request! ", e);
        }
        return new HashMap<>();
    }

    public static Map<String, String> makeRequestWithHeaders(HttpRequestBase request, Map<String, String> headers) throws Exception {
        logger.info("Sending [{}] request to [{}]", request.getMethod(), request.getURI());

        // add request headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.addHeader(header.getKey(), header.getValue());
        }

        // store response
        Map<String, String> responseMap;
        // get response details
        try (CloseableHttpClient httpClient = httpClient()) {
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                responseMap = getResponseDetails(response);
            }
        }
        return responseMap;
    }

    private static CloseableHttpClient httpClient() {
        HttpClientBuilder builder = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build());

        if (ignoreInvalidCerts) {
            try {
                builder.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            } catch (Exception e) {
                fail("Error configuring HTTP client", e);
            }
        }

        return builder.build();
    }

    private static String getSessionCookie(Header[] headers) {
        String sessionCookie = "";

        for (Header header : headers) {

            if (header.getName().equals("Set-Cookie")) {
                sessionCookie = header.getValue();
            }
        }
        return sessionCookie;
    }

    public static Map<String, String> getResponseDetails(CloseableHttpResponse response) {
        Map<String, String> responseMap = new HashMap<>();
        String responsePayload = "";

        // get response code
        responseMap.put("responseCode", String.valueOf(response.getStatusLine().getStatusCode()));

        // get response headers
        responseMap.put("Set-Cookie", getSessionCookie(response.getAllHeaders()));

        // get response body
        HttpEntity entity = response.getEntity();
        if (entity != null) {

            // return response payload as String
            try {
                responsePayload = EntityUtils.toString(entity);
            } catch (IOException e) {
                logger.error("Exception trying to get response detail", e);
            }

            // add response to map
            responseMap.put("responsePayload", responsePayload);
        }

        logger.info("Got [{}] response", response.getStatusLine().getStatusCode());
        if (responsePayload.length() > 500){
            responsePayload = responsePayload.substring(0,500);
        }
//        logger.info("Response payload [{}]", responsePayload);

        // stop test on failure response (as this will cause failure downstream - better to fail early!)
        int statusCode = response.getStatusLine().getStatusCode();
        if (!(statusCode == 200 || statusCode == 201)) {
            fail("Unexpected [" + statusCode + "] response for API call!");
        }

        return responseMap;
    }
}
