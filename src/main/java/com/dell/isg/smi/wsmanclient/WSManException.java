/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

public class WSManException extends Exception {
    private static final long serialVersionUID = 432039798151179100L;


    public WSManException() {
        super();
    }


    public WSManException(String message) {
        super(message);
    }
}
