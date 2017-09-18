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
package com.dataknowl.dcs.sdk.sms;

import com.dataknowl.dcs.sdk.client.DcsAuth;
import com.dataknowl.dcs.sdk.client.Region;
import com.dataknowl.dcs.sdk.client.Signature;
import com.dataknowl.dcs.sdk.http.ClientHttpPostException;
import com.dataknowl.dcs.sdk.http.HttpPostFace;
import com.dataknowl.dcs.sdk.http.HttpResponseObj;
import com.dataknowl.dcs.sdk.vagent.ServiceClient;
import com.dataknowl.dcs.sdk.sms.model.SmsSendResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Complexity Intelligence, LLC
 */


public class SmsClient implements ServiceClient {

    private final DcsAuth dcsAuth;
    private final Region region;
            
    private final Logger logger = LoggerFactory.getLogger(SmsClient.class);
    
    public SmsClient(DcsAuth dcsAuth, 
            Region region) {

        this.dcsAuth = dcsAuth;
        this.region = region;
    }

    public SmsSendResult send(String fromId, String to, String body) {
        
        // String method
        String resource = "/v1/message/sms";
        
        // Parameters
        
        // Get Current Timestamp
        long timestampTs = System.currentTimeMillis();

        String timestamp = String.valueOf(timestampTs);
        
        SmsSendResult ssr = new SmsSendResult();
        
        // Replace method placeholders (if any)
        
        logger.debug("Resource {}", resource);

        HttpPostFace pf = new HttpPostFace();

        HttpResponseObj response = null;

        try {

            URL url = new URL("https://" + region.getUri() + ".dcs.dataknowl.com" + resource);

            HashMap<String, String> params = new HashMap<>();
            params.put("from:id", fromId);
            params.put("to", to);
            params.put("body", body);
            
            String signature = Signature.calculate(dcsAuth.getAccessKeyId(), 
                    dcsAuth.getSecretAccessKey(), "POST", 
                    "application/x-www-form-urlencoded", 
                    resource,
                    timestampTs, params);
            
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Authorization", "DCS-V1");
            headers.put("AccessKeyId", dcsAuth.getAccessKeyId());
            headers.put("Timestamp", timestamp);
            headers.put("Signature", signature);            
            
            response = pf.doPost(url, params, headers);
         
        } catch (MalformedURLException mue) {
            logger.error("", mue);
        } catch (ClientHttpPostException cpe) {
            logger.error("", cpe);
        }

        String content = response.getHttpResponseContent();
        
        logger.debug("Reponse = {}", content);

        SAXBuilder builder = new SAXBuilder();
        try {

            Document document = builder.build(new ByteArrayInputStream(content.getBytes("UTF-8")));

            Element rootE = document.getRootElement();

            Element resultE = rootE.getChild("result");

            Element smsE = resultE.getChild("sms");
            
            Element statusE = smsE.getChild("status");
           
            String code = statusE.getAttributeValue("code");
            String msg = statusE.getAttributeValue("msg");

            ssr.setStatus(Integer.parseInt(code));
            ssr.setMessage(msg);

            logger.debug("Code = {} Msg = {}", ssr.getStatus(), ssr.getMessage());

        } catch (JDOMException e) {
            logger.error("Error while parsing XML", e);
        } catch (IOException e) {
            logger.error("Error while parsing XML", e);
        }

        return (ssr);
    }
}
