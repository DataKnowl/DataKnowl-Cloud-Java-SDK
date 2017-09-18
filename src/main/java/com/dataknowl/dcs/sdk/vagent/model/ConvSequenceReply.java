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
package com.dataknowl.dcs.sdk.vagent.model;

import java.util.List;

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class ConvSequenceReply extends ConvSequence {

    // Text (required)
    private final List<String> textList;

    private ConvSequenceReply(ConvSequenceReplyBuilder builder) {
        super.setRid(builder.rid);
        this.textList = builder.textList;
    }

    public List<String> getTextList() {
        return (this.textList);
    }

    public static class ConvSequenceReplyBuilder {

        private final Rid rid;
        
        private final List<String> textList;

        public ConvSequenceReplyBuilder(Rid rid, List<String> textList) {
            this.rid = rid;
            this.textList = textList;
        }
        
        //Return the finally constructed object
        public ConvSequenceReply build() {
            ConvSequenceReply user = new ConvSequenceReply(this);
            validateUserObject(user);
            return user;
        }
        
        private void validateUserObject(ConvSequenceReply sequence) {
            //Do some basic validations to check 
            //if user object does not break any assumption of system
        }        
    }

}
