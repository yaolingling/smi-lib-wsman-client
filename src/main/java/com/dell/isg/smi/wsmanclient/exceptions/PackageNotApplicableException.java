/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.exceptions;

public class PackageNotApplicableException extends Exception {

    private static final long serialVersionUID = -6806000114791012000L;


    /**
     * Constructs a new VCenter Connection Exception
     */
    public PackageNotApplicableException() {
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param message The Exception message
     */
    public PackageNotApplicableException(String message) {
        super(message);
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param cause The cause of the Exception
     */
    public PackageNotApplicableException(Throwable cause) {
        super(cause);
    }


    /**
     * Constructs a new VCenter Connection Exception
     * 
     * @param message The Exception message
     * @param cause The cause of the Exception
     */
    public PackageNotApplicableException(String message, Throwable cause) {
        super(message, cause);
    }

}
