/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

/**
 * Non user-facing RestCommon exceptions.
 */
public class WSManRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -5377947470743177036L;


    public WSManRuntimeException() {
    }


    public WSManRuntimeException(String message) {
        super(message);
    }


    public WSManRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }


    public WSManRuntimeException(Throwable cause) {
        super(cause);
    }


    public WSManRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
