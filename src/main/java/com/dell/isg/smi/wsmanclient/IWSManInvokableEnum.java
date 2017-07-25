/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

/**
 * The Interface IWSManInvokableEnum.
 */
public interface IWSManInvokableEnum {

    /**
     * Gets the command enum.
     *
     * @return the command enum
     */
    public abstract IWSManClassEnum getCommandEnum();
    
    /**
     * Gets the system name.
     *
     * @return the system name
     */
    public abstract String getSystemName();
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public abstract String getName();
    
    /**
     * Gets the creation class name.
     *
     * @return the creation class name
     */
    public abstract String getCreationClassName();
    
    /**
     * Gets the system creation class name.
     *
     * @return the system creation class name
     */
    public abstract String getSystemCreationClassName();
    
    /**
     * Gets the element name.
     *
     * @return the element name
     */
    public abstract String getElementName();
}
