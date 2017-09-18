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

/**
 *
 * @author Complexity Intelligence, LLC
 */
public class ConvSequenceInput extends ConvSequence {

    // Text (required)
    private final String text;

    private ConvSequenceInput(ConvSequenceInputBuilder builder) {
        super.setRid(builder.rid);
        this.text = builder.text;
    }

    public String getText() {
        return (this.text);
    }

    public static class ConvSequenceInputBuilder {

        private final Rid rid;
        
        private final String text;

        public ConvSequenceInputBuilder(Rid rid, String text) {
            this.rid = rid;
            this.text = text;
        }
        
        //Return the finally constructed object
        public ConvSequenceInput build() {
            ConvSequenceInput user = new ConvSequenceInput(this);
            validateUserObject(user);
            return user;
        }
        
        private void validateUserObject(ConvSequenceInput sequence) {
            //Do some basic validations to check 
            //if user object does not break any assumption of system
        }        
    }

}
