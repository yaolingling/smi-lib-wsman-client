/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

/**
 * The Interface ICredential.
 */
public interface ICredential {

    /**
     * Gets the ID.
     *
     * @return the id
     */
    long getId();


    /**
     * Sets the Id.
     *
     * @param id the new id
     */
    void setId(long id);


    /**
     * Gets the label.
     *
     * @return String - the label
     */
    String getLabel();


    /**
     * Sets the label.
     *
     * @param value - the label
     */
    void setLabel(String value);


    /**
     * Validate method.
     *
     * @return ValidationResult - the validation result object
     */
    ValidationResult validate();

}
