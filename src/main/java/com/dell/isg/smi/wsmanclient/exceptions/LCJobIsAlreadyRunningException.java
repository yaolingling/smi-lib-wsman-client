/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.exceptions;

public class LCJobIsAlreadyRunningException extends Exception {

    private static final long serialVersionUID = -6806000114791013007L;


    /**
     * Constructs a new VCenter Connection Exception
     */
    public LCJobIsAlreadyRunningException() {
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param message The Exception message
     */
    public LCJobIsAlreadyRunningException(String message) {
        super(message);
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param cause The cause of the Exception
     */
    public LCJobIsAlreadyRunningException(Throwable cause) {
        super(cause);
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param message The Exception message
     * @param cause The cause of the Exception
     */
    public LCJobIsAlreadyRunningException(String message, Throwable cause) {
        super(message, cause);
    }

}
