/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

public class InvokeCmdResponse {

    int returnValue;
    String messageId;
    String message;


    public int getReturnValue() {
        return returnValue;
    }


    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }


    public String getMessageId() {
        return messageId;
    }


    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }

}
