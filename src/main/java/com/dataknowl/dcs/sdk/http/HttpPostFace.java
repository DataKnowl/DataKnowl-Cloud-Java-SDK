/*
 * The MIT License
 *
 * Copyright 2017 Complexity Intelligence, LLC.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dataknowl.dcs.sdk.http;

import org.apache.http.client.methods.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
import org.apache.http.Header;
import java.net.URL;
import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class HttpPostFace {

    // CharSet definition
    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");    
    // Logger
    final Logger logger = LoggerFactory.getLogger(HttpPostFace.class);

    public HttpResponseObj doPost(URL url, HashMap<String, String> parameters)
            throws ClientHttpPostException, ClientHttpPostHostConnectException {

        return (doPost(url, parameters, new HashMap<String, String>()));
    }

    public HttpResponseObj doPost(URL url, HashMap<String, String> parameters,
            HashMap<String, String> headers) throws
            ClientHttpPostException, ClientHttpPostHostConnectException {

        HttpResponseObj responseObj = null;

        logger.debug("Posting: " + url.toString());

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url.toString());

        // Check if there are header to add...
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {

                httppost.addHeader(entry.getKey(), entry.getValue());

                logger.debug("Adding Header: {}={}", entry.getKey(),
                        entry.getValue());
            }
        } else {
            logger.debug("No Headers to add.");
        }

        // Http Status Code
        int httpStatusCode = 0;

        // Http Response Content
        String httpResponseContent = null;

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(
                        entry.getKey(),
                        new String(entry.getValue().getBytes(UTF8_CHARSET), UTF8_CHARSET)
                        ));

                logger.debug("Adding parameters: {}={}", entry.getKey(),
                        entry.getValue());
            }

            if(parameters.isEmpty()) {
                logger.debug("No parameters to add...");
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
            entity.setContentType("application/x-www-form-urlencoded");
            httppost.setEntity(entity);

            // Execute HTTP Post Request
            HttpResponse httpResponse = httpclient.execute(httppost);
            ///HttpEntity resEntity = httpResponse.getEntity();

            httpStatusCode = httpResponse.getStatusLine().getStatusCode();

            Header[] contentTypeHeader = httpResponse.getHeaders("Content-Type");
            String contentType = contentTypeHeader[0].getValue();
            logger.debug("Returned content type: " + contentType);

            StringBuilder result = new StringBuilder();
            if (httpStatusCode == HttpStatus.SC_OK) {
                InputStream is = httpResponse.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // Create an empty String Builder

                String line = null;
                while ((line = br.readLine()) != null) {
                    logger.debug("LINE={}", line);
                    result.append(line);
                }
                httpResponseContent = result.toString();
                logger.debug("Result: " + httpResponseContent);
                responseObj = new HttpResponseObj(httpStatusCode, httpResponseContent, contentType);
            } else {
                logger.debug("HTTP ERROR CODE {} {}", httpStatusCode, result.toString());
                throw new ClientHttpPostHostConnectException("Error: Status code is not OK: " + httpStatusCode + ":" + result.toString());
            }

            httpclient.getConnectionManager().shutdown();

        } catch (HttpHostConnectException hhcex) {

            logger.error("HTTPHOSTCONNECTEXCEPTION... Connection to (host) refused...", hhcex);

            // The response is empty
            httpResponseContent = "";
            // The code is for an error
            httpStatusCode = HttpStatus.SC_SERVICE_UNAVAILABLE;

            throw new ClientHttpPostHostConnectException("Error: Status code is not OK");

        } catch (IOException e) {
            logger.error("IOException", e);
            throw new ClientHttpPostException("Error: IO Error");
        } catch (Exception e) {
            logger.error("Exception " + e.getMessage(), e);
            throw new ClientHttpPostException("(Error) Generic Error " + e.getMessage());
        }

        return (responseObj);
    }
}