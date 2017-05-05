/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.exceptions;

public class PackageNotSupportedException extends Exception {

    private static final long serialVersionUID = -6806000114791013000L;


    /**
     * Constructs a new VCenter Connection Exception
     */
    public PackageNotSupportedException() {
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param message The Exception message
     */
    public PackageNotSupportedException(String message) {
        super(message);
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param cause The cause of the Exception
     */
    public PackageNotSupportedException(Throwable cause) {
        super(cause);
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param message The Exception message
     * @param cause The cause of the Exception
     */
    public PackageNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

}
