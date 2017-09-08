/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl.model;

import java.io.Serializable;

public class LicensableDevice implements Serializable {
    private static final long serialVersionUID = -2262111594350042278L;


    public LicensableDevice() {
    }

    long id;


    public Long getId() {
        return id;
    }

    private DeviceLicense deviceLicense;

    String deviceId = "";

    String deviceStatus = "";

    String deviceStatusMessage = "";

    String FQDD = "";

    String model = "";


    public String getDeviceID() {
        return deviceId;
    }


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getDeviceStatus() {
        return deviceStatus;
    }


    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }


    public String getDeviceStatusMessage() {
        return deviceStatusMessage;
    }


    public void setDeviceStatusMessage(String deviceStatusMessage) {
        this.deviceStatusMessage = deviceStatusMessage;
    }


    public String getFQDD() {
        return FQDD;
    }


    public void setFQDD(String fQDD) {
        FQDD = fQDD;
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public DeviceLicense getDeviceLicense() {
        return deviceLicense;
    }


    public void setDeviceLicense(DeviceLicense deviceLicense) {
        this.deviceLicense = deviceLicense;
    }

}
