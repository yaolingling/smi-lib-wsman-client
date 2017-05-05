/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PasswordCredential", propOrder = { "id", "label", "username", "password", "domain" })
@XmlRootElement
public class PasswordCredential implements IPasswordCredential {

    // *************** Windows Username Requirements ***********************
    // between 1 and 64 characters long
    // no non-printable characters, so start at x20 (space) in the ascii table
    // invalid characters: " / \ [ ] : ; | = , + * ? < >
    // exclude the @ sign to avoid confusion with user@domain
    @XmlTransient
    public static final String WINDOWS_USERNAME_REGEX = "^(?![\\x20.]+$)([^\\\\/\"\\[\\]:|<>+=;,?*@]{1,64})$";

    // ************** Windows Password Requirements ************************
    // between 0 and 127 characters long (Note: Blank Password allowed)
    // any character, printable and non-printable is allowed
    @XmlTransient
    public static final String WINDOWS_PASSWORD_REGEX = "^.{0,127}$";

    // ***************Windows Domain Requirements **************************
    // between 1 and 64 characters long
    // first character must be alphabetical or numeric
    // last character must not be a minus sign or a period
    // cannot contain a space
    // invalid characters: " / \ : | , * ? < > ~ ! @ # $ % ^ & ' ( ) { } _
    @XmlTransient
    public static final String WINDOWS_DOMAIN_REGEX = "^[a-zA-Z](?![\\x20.]+$)([^\\\\/\"\\[\\]:|<>~!@#$%&*^',?(){}_ ]{1,64})[^.-]$";

    @XmlElement(required = true)
    long id;
    @XmlElement(required = true)
    String label;
    @XmlElement(required = true)
    String username;
    @XmlElement(required = true)
    String password;
    String domain;


    /**
     * @see ICredential.getId()
     */
    @Override
    public long getId() {
        return this.id;
    }


    /**
     * @see ICredential.setId(long)
     */
    @Override
    public void setId(long value) {
        this.id = value;
    }


    /**
     * @see ICredential.getLabel()
     */
    @Override
    public String getLabel() {
        return this.label;
    }


    /**
     * @see ICredential.setLabel(String)
     */
    @Override
    public void setLabel(String value) {
        this.label = (value == null) ? null : value.trim();
    }


    /**
     * @see IPasswordCredential.getUsername()
     */
    @Override
    public String getUsername() {
        return this.username;
    }


    /**
     * @see IPasswordCredential.setUsername(String)
     */
    @Override
    public void setUsername(String value) {
        this.username = (value == null) ? null : value.trim();
    }


    /**
     * @see IPasswordCredential.getPassword()
     */
    @Override
    public String getPassword() {
        return this.password;
    }


    /**
     * @see IPasswordCredential.setPassword(String)
     */
    @Override
    public void setPassword(String value) {
        this.password = (value == null) ? null : value.trim();
    }


    /**
     * @see IPasswordCredential.getDomain()
     */
    @Override
    public String getDomain() {
        return this.domain;
    }


    /**
     * @see IPasswordCredential.setDomain(String)
     */
    @Override
    public void setDomain(String value) {
        this.domain = (value == null) ? null : value.trim();
    }


    /**
     * @see ICredential.validate()
     */
    @Override
    public ValidationResult validate() {
        String message = null;
        String usr = getUsername();
        String pwd = getPassword();

        ValidationResult validationResult = new ValidationResult();
        validationResult.setValid(usr != null && usr.length() > 0 && pwd != null && pwd.length() > 0);
        validationResult.setMessage(message);
        return validationResult;
    }


    /**
     * Gets the username and domain in the format of username@domain
     *
     * @return String - the username@domain. If no domain present, just the username
     */
    public String getUsernameAtDomain() {
        String usr = getUsername();
        String dom = getDomain();
        if ((null != usr) && (usr.length() > 0)) {
            if ((null != dom) && (dom.length() > 0)) {
                return String.format("%s@%s", usr, dom);
            } else {
                return usr;
            }
        }
        return null;
    }

}
