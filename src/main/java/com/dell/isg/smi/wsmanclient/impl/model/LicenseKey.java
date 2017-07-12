/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl.model;

import java.io.Serializable;

public class LicenseKey implements Serializable {

    private static final long serialVersionUID = -2256111594350042278L;


    public LicenseKey() {
        entitlementId = "";
        hostId = "";
    }


    public LicenseKey(String entitlementId, String hostId) {
        this.entitlementId = entitlementId;
        this.hostId = hostId;
    }


    public String getEntitlementId() {
        return entitlementId;
    }


    public void setEntitlementId(String entitlementId) {
        this.entitlementId = entitlementId;
    }


    public String getHostId() {
        return hostId;
    }


    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    String entitlementId;
    String hostId;
}
