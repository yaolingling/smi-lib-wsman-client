/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "WSManPasswordCredential")
public class WSManPasswordCredential extends PasswordCredential {

    // ********************** IDRAC Username Requirements ********************************
    // between 1 and 16 characters long
    // no non-printable characters, so start at x20 (space) in the ascii table
    // invalid characters: " / \ ~ ` .
    public static final String IDRAC_USERNAME_REGEX = "^(?![\\x20.]+$)([^\\\\/\"@~`.]{1,16})$";;

    // ********************** IDRAC Password Requirements ********************************
    // Non-printable characters are not allowed - ascii x20(space) and above allowed;
    // characters `,^ and space are not allowed;
    // from 1 thru 20 characters in length
    public static final String IDRAC_PASSWORD_REGEX = "^(?![\\x20]+$)([^`^ ]{1,20})$";

    private boolean cnCheck = false;

    private boolean caCheck = false;


    public boolean isCnCheck() {
        return cnCheck;
    }


    public void setCnCheck(boolean cnCheck) {
        this.cnCheck = cnCheck;
    }


    public boolean isCaCheck() {
        return caCheck;
    }


    public void setCaCheck(boolean caCheck) {
        this.caCheck = caCheck;
    }


    /**
     * @see ICredential.validate()
     */
    @Override
    public ValidationResult validate() {
        String message = null;

        // username
        boolean usernameValid = false;
        String usernameMsg = validateUsername(getUsername());
        if (usernameMsg.length() > 0) {
            message = usernameMsg;
        } else {
            usernameValid = true;
        }

        // password
        boolean passwordValid = false;
        String passwordMsg = validatePassword(getPassword());
        if (passwordMsg.length() > 0) {
            if (null != message) {
                message += ", ";
            }
            message += passwordMsg;
        } else {
            passwordValid = true;
        }

        // domain
        boolean domainValid = false;
        String domainMsg = validateDomain(getDomain());
        if (domainMsg.length() > 0) {
            if (null != message) {
                message += ", ";
            }
            message += domainMsg;
        } else {
            domainValid = true;
        }

        ValidationResult validationResult = new ValidationResult();
        validationResult.setValid(usernameValid && passwordValid && domainValid);
        validationResult.setMessage(message);
        return validationResult;
    }


    // ********************* Private Methods *************************************

    private String validateUsername(String value) {
        if (null == value) {
            return "Username cannot be null.";
        }
        if (!value.matches(IDRAC_USERNAME_REGEX)) {
            return "username does not meet iDRAC requirements";
        }
        if (!value.matches(WINDOWS_USERNAME_REGEX)) {
            return "username does not meet Windows requirements";
        }
        return "";
    }


    private String validatePassword(String value) {
        if (null == value) {
            return "Password cannot be null.";
        }
        if (!value.matches(IDRAC_PASSWORD_REGEX)) {
            return "password does not meet iDRAC requirements";
        }
        if (!value.matches(WINDOWS_PASSWORD_REGEX)) {
            return "password does not meet Windows requirements";
        }
        return "";
    }


    private String validateDomain(String value) {
        if ((null != value) && (value.length() > 0)) {
            if (!value.matches(WINDOWS_DOMAIN_REGEX)) {
                return "domain does not meet Windows requirements";
            }
        }
        return "";
    }

}
