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
package com.dataknowl.dcs.sdk.client;

import com.dataknowl.utils.HashUtil;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class Signature {

    private final static Logger logger = LoggerFactory.getLogger(Signature.class);

    public static String calculate(String accessKeyId, String secretAccessKey,
            String method, String contentType, String resource, long timestamp,
            Map<String, String> params) {

        String signature = "";

        String paramsMD5 = calculateParamsMD5(params);
        String stringToSign = accessKeyId + ":" + method + ":"
                + contentType + ":" + resource + ":" + paramsMD5 + ":" + String.valueOf(timestamp);

        String hmacsha256 = HashUtil.getHmacSHA256(stringToSign, secretAccessKey);

        logger.debug("String HMAC-SHA256 = {}", hmacsha256);
        
        signature = hmacsha256;

        logger.debug("Signature = {}", signature);

        return (signature);
    }

    private static String calculateParamsMD5(Map<String, String> params) {

        StringBuilder b = new StringBuilder();

        Map<String, String> treeMap = new TreeMap<>(params);

        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            logger.debug("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());

            b.append(entry.getKey());
            b.append("=");
            b.append(entry.getValue());
            b.append("&");
        }

        String paramsToSign = b.toString();

        paramsToSign = paramsToSign.substring(0, paramsToSign.length() - 1);

        logger.debug("ParamsToSign={}", paramsToSign);

        String md5 = HashUtil.getMD5Digest(paramsToSign);

        logger.debug("MD5 of Params={}", md5);

        return (md5);
    }
}
