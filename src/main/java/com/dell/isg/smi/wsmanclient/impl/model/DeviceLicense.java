/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.impl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceLicense implements Serializable {

    public DeviceLicense() {
    }


    public DeviceLicense(LicenseKey key) {
        this.licenseKey = key;
    }

    private static final long serialVersionUID = -2254001594350042278L;


    public String getiDracIp() {
        return iDracIp;
    }


    public void setiDracIp(String iDracIp) {
        this.iDracIp = iDracIp;
    }


    public String getEvalLicenseTimeRemaining() {
        return evalLicenseTimeRemaining;
    }


    public void setEvalLicenseTimeRemaining(String evalLicenseTimeRemaining) {
        this.evalLicenseTimeRemaining = evalLicenseTimeRemaining;
    }


    public String getLicenseAttributes() {
        return licenseAttributes;
    }


    public void setLicenseAttributes(String licenseAttributes) {
        if (licenseAttributes.equals("0")) {
            this.licenseAttributes = "Unbound";
        } else if (licenseAttributes.equals("1")) {
            this.licenseAttributes = "Bound";
        } else {
            this.licenseAttributes = licenseAttributes;
        }
    }


    public String getLicenseDescription() {
        return licenseDescription;
    }


    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }


    public String getLicenseDuration() {
        return licenseDuration;
    }


    public void setLicenseDuration(String licenseDuration) {
        this.licenseDuration = licenseDuration;
    }


    public String getLicenseEndDate() {
        return licenseEndDate;
    }


    public void setLicenseEndDate(String licenseEndDate) {
        this.licenseEndDate = licenseEndDate;
    }


    public String getLicenseInstallDate() {
        return licenseInstallDate;
    }


    public void setLicenseInstallDate(String licenseInstallDate) {
        this.licenseInstallDate = licenseInstallDate;
    }


    public String getLicensePrimaryStatus() {
        return licensePrimaryStatus;
    }


    public void setLicensePrimaryStatus(String licensePrimaryStatus) {
        if (licensePrimaryStatus.equals("0")) {
            this.licensePrimaryStatus = "Unknown";
        } else if (licensePrimaryStatus.equals("1")) {
            this.licensePrimaryStatus = "OK";
        } else if (licensePrimaryStatus.equals("2")) {
            this.licensePrimaryStatus = "Warning";
        } else if (licensePrimaryStatus.equals("3")) {
            this.licensePrimaryStatus = "Critical";
        } else {
            this.licensePrimaryStatus = licensePrimaryStatus;
        }
    }


    public String getLicenseSoldDate() {
        return licenseSoldDate;
    }


    public void setLicenseSoldDate(String licenseSoldDate) {
        this.licenseSoldDate = licenseSoldDate;
    }


    public String getLicenseStartDate() {
        return licenseStartDate;
    }


    public void setLicenseStartDate(String licenseStartDate) {
        this.licenseStartDate = licenseStartDate;
    }


    public String getLicenseStatusMessage() {
        return licenseStatusMessage;
    }


    public void setLicenseStatusMessage(String licenseStatusMessage) {
        this.licenseStatusMessage = licenseStatusMessage;
    }


    public String getLicenseType() {
        return licenseType;
    }


    public void setLicenseType(String licenseType) {
        if (licenseType.equals("1")) {
            this.licenseType = "Perpertual";
        } else if (licenseType.equals("2")) {
            this.licenseType = "Leased";
        } else if (licenseType.equals("3")) {
            this.licenseType = "Evaluation";
        } else if (licenseType.equals("4")) {
            this.licenseType = "Site";
        } else {
            this.licenseType = licenseType;
        }
    }


    public String getLicenseFile() {
        return licenseFile;
    }


    public void setLicenseFile(String licenseFile) {
        this.licenseFile = licenseFile;
    }


    public List<LicensableDevice> getDevices() {
        return devices;
    }


    public void setDevices(List<LicensableDevice> devices) {
        this.devices = devices;
    }


    public LicenseKey getLicenseKey() {
        return licenseKey;
    }


    public void setLicenseKey(LicenseKey licenseKey) {
        this.licenseKey = licenseKey;
    }

    LicenseKey licenseKey;

    String iDracIp = "";
    String licenseType = "";
    String licenseSoldDate = "";
    String licenseInstallDate = "";

    String licenseStartDate = "";

    String licenseEndDate = "";

    String licenseDuration = "";

    String evalLicenseTimeRemaining = "";

    String licenseDescription = "";

    String licensePrimaryStatus = "";

    String licenseStatusMessage = "";

    String licenseAttributes = "";

    String licenseFile = "";

    String featureBits = "";

    String serviceTag = "";


    /**
     * @return the serviceTag
     */
    public String getServiceTag() {
        return serviceTag;
    }


    /**
     * @param serviceTag the serviceTag to set
     */
    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }


    /**
     * @return the featureBits
     */
    public String getFeatureBits() {
        return featureBits;
    }


    /**
     * @param featureBits the featureBits to set
     */
    public void setFeatureBits(String featureBits) {
        this.featureBits = featureBits;
    }

    List<LicensableDevice> devices = new ArrayList<LicensableDevice>();
}
