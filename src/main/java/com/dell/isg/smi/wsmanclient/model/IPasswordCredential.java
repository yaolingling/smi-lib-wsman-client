/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

/**
 * The Interface IPasswordCredential.
 */
public interface IPasswordCredential extends ICredential {

    /**
     * Gets the username.
     *
     * @return String - the username
     */
    public String getUsername();


    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username);


    /**
     * Gets the password.
     *
     * @return String - the password
     */
    public String getPassword();


    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password);


    /**
     * Gets the Domain.
     *
     * @return String - the domain
     */
    public String getDomain();


    /**
     * Sets the domain.
     *
     * @param domain the new domain
     */
    public void setDomain(String domain);
}
