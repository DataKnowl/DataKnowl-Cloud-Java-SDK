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
package com.dataknowl.dcs.sdk.vagent;

import com.dataknowl.dcs.sdk.client.DcsAuth;
import com.dataknowl.dcs.sdk.client.Region;
import com.dataknowl.dcs.sdk.client.Signature;
import com.dataknowl.dcs.sdk.http.ClientHttpPostException;
import com.dataknowl.dcs.sdk.http.HttpPostFace;
import com.dataknowl.dcs.sdk.http.HttpResponseObj;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequenceResult;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequence;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequenceInput;
import com.dataknowl.dcs.sdk.vagent.model.ConvSequenceReply;
import com.dataknowl.dcs.sdk.vagent.model.Rid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


public class VAgentClient implements ServiceClient {

    private final String rid;
    private final String channelId;
    private final DcsAuth dcsAuth;
    private final Region region;
            
    private final Logger logger = LoggerFactory.getLogger(VAgentClient.class);
    
    public VAgentClient(String rid, String channelId, DcsAuth dcsAuth, 
            Region region) {
        this.rid = rid;
        this.channelId = channelId;
        this.dcsAuth = dcsAuth;
        this.region = region;
    }

    public ConvSequenceResult processConvSequence(ConvSequenceInput sequence) {
        
        // String method
        String resource = "/v1/vagent/{VAGNT}/conv/{VACNV}/sequence/process";
        
        // Parameters
        String convRid = sequence.getRid().get();
        String sequenceText = sequence.getText();
        
        // Get Current Timestamp
        long timestampTs = System.currentTimeMillis();

        String timestamp = String.valueOf(timestampTs);
        
        ConvSequenceResult r = new ConvSequenceResult();
        
        // Replace method placeholders
        resource = resource.replace("{VAGNT}", this.rid);
        resource = resource.replace("{VACNV}", convRid);
        
        logger.debug("Resource {}", resource);

        HttpPostFace pf = new HttpPostFace();

        HttpResponseObj response = null;

        try {

            URL url = new URL("https://" + region.getUri() + ".dcs.dataknowl.com" + resource);

            HashMap<String, String> params = new HashMap<>();
            params.put("channel:id", channelId);
            params.put("conv:seq:text", sequenceText);
            
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

        SAXBuilder builder = new SAXBuilder();
        try {

            Document document = builder.build(new ByteArrayInputStream(content.getBytes("UTF-8")));

            Element rootE = document.getRootElement();

            Element resultE = rootE.getChild("result");

            Element processingE = resultE.getChild("processing");
            
            Element conversationE = processingE.getChild("conversation");
           
            String rid = conversationE.getChildTextTrim("rid");

            Element replyE = processingE.getChild("reply");

            Element sequenceE = replyE.getChild("sequence");
            
            List<String> textList = new ArrayList<String>();
            
            List<Element> textL = sequenceE.getChildren("text");
            
            for(Element textE : textL) {
                textList.add(textE.getTextTrim());
            }

            ConvSequenceReply replySequence = new ConvSequenceReply.ConvSequenceReplyBuilder(new Rid(rid), textList).build();

            r.setReplySequence(replySequence);

            logger.debug("Text = {}", textList);

        } catch (JDOMException e) {
            logger.error("Error while parsing XML", e);
        } catch (IOException e) {
            logger.error("Error while parsing XML", e);
        }

        return (r);
    }
}
