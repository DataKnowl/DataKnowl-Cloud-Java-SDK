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
package com.dataknowl.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 * Hashing Utils - Full UTF8 Support
 *
 * @author Complexity Intelligence, LLC
 */
public class HashUtil {

    public static String getSHA256Digest(String input) {

        String sha256val = "";
        MessageDigest algorithm = null;

        try {
            algorithm = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("Cannot find digest algorithm");
            System.exit(1);
        }

        byte[] defaultBytes = null;
        
        try {
            defaultBytes = input.getBytes("UTF8");
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace();
        }

        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        sha256val = hexString.toString();
        return (sha256val);

    }

    public static String getMD5Digest(String input) {

        String sha256val = "";
        MessageDigest algorithm = null;

        try {
            algorithm = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println("Cannot find digest algorithm");
            System.exit(1);
        }

        byte[] defaultBytes = null;
        
        try {
            defaultBytes = input.getBytes("UTF8");
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace();
        }
        
        algorithm.reset();
        algorithm.update(defaultBytes);
        byte messageDigest[] = algorithm.digest();
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        sha256val = hexString.toString();
        return (sha256val);

    }

    public static String getHmacSHA256(String strToHash, String secretAuthKey) {

        String hexBytes = null;

        try {

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretAuthKey.getBytes("UTF8"),
                    "HmacSHA256");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);

            byte[] rawHmacBytes = mac.doFinal(strToHash.getBytes("UTF8"));

            hexBytes = new String(Hex.encodeHex(rawHmacBytes));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (hexBytes);
    }
}