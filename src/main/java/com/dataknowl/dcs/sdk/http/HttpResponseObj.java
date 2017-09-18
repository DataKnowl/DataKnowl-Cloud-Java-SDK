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

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class HttpResponseObj {

    private int httpResponseCode;
    private String httpResponseContent;
    private String httpHeaderContentType;

    public HttpResponseObj(int code, String content) {
        this.httpResponseCode = code;
        this.httpResponseContent = content;
        this.httpHeaderContentType = null;
    }

    public HttpResponseObj(int code, String content, String contentType) {
        this.httpResponseCode = code;
        this.httpResponseContent = content;
        this.httpHeaderContentType = contentType;
    }

    /**
     * @return the httpResponseCode
     */
    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    /**
     * @return the httpResponseContent
     */
    public String getHttpResponseContent() {
        return httpResponseContent;
    }

    /**
     * @return the httpHeaderContentType
     */
    public String getHttpHeaderContentType() {
        return httpHeaderContentType;
    }

    /**
     * @param httpHeaderContentType the httpHeaderContentType to set
     */
    public void setHttpHeaderContentType(String httpHeaderContentType) {
        this.httpHeaderContentType = httpHeaderContentType;
    }
}
