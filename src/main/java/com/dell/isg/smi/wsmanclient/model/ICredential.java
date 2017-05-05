/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

public interface ICredential {

    /**
     * Gets the ID
     *
     * @Return long - id
     */
    long getId();


    /**
     * Sets the Id
     *
     * @param value - the Id
     */
    void setId(long id);


    /**
     * Gets the label
     *
     * @return String - the label
     */
    String getLabel();


    /**
     * Sets the label
     *
     * @param value - the label
     */
    void setLabel(String value);


    /**
     * Validate method
     *
     * @return ValidationResult - the validation result object
     */
    ValidationResult validate();

}
