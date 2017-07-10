package com.dell.isg.smi.wsmanclient.impl.model;

import java.io.Serializable;

public class IDRACCardStringView implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String attributeDisplayName = null;
    private String attributeName = null;
    private String currentValue = null;
    private String defaultValue = null;
    private String dependency = null;
    private String displayOrder = null;
    private String fQDD = null;
    private String groupDisplayName = null;
    private String groupID = null;
    private String instanceID = null;
    private String isReadOnly = null;
    private String maxLength = null;
    private String minLength = null;
    private String pendingValue = null;

    /**
     * @return the attributeDisplayName
     */
    public String getAttributeDisplayName() {
        return attributeDisplayName;
    }

    /**
     * @param attributeDisplayName the attributeDisplayName to set
     */
    public void setAttributeDisplayName(String attributeDisplayName) {
        this.attributeDisplayName = attributeDisplayName;
    }

    /**
     * @return the attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName the attributeName to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return the currentValue
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * @param currentValue the currentValue to set
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the dependency
     */
    public String getDependency() {
        return dependency;
    }

    /**
     * @param dependency the dependency to set
     */
    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    /**
     * @return the displayOrder
     */
    public String getDisplayOrder() {
        return displayOrder;
    }

    /**
     * @param displayOrder the displayOrder to set
     */
    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * @return the fQDD
     */
    public String getfQDD() {
        return fQDD;
    }

    /**
     * @param fQDD the fQDD to set
     */
    public void setfQDD(String fQDD) {
        this.fQDD = fQDD;
    }

    /**
     * @return the groupDisplayName
     */
    public String getGroupDisplayName() {
        return groupDisplayName;
    }

    /**
     * @param groupDisplayName the groupDisplayName to set
     */
    public void setGroupDisplayName(String groupDisplayName) {
        this.groupDisplayName = groupDisplayName;
    }

    /**
     * @return the groupID
     */
    public String getGroupID() {
        return groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    /**
     * @return the instanceID
     */
    public String getInstanceID() {
        return instanceID;
    }

    /**
     * @param instanceID the instanceID to set
     */
    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    /**
     * @return the isReadOnly
     */
    public String getIsReadOnly() {
        return isReadOnly;
    }

    /**
     * @param isReadOnly the isReadOnly to set
     */
    public void setIsReadOnly(String isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    /**
     * @return the maxLength
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the minLength
     */
    public String getMinLength() {
        return minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    /**
     * @return the pendingValue
     */
    public String getPendingValue() {
        return pendingValue;
    }

    /**
     * @param pendingValue the pendingValue to set
     */
    public void setPendingValue(String pendingValue) {
        this.pendingValue = pendingValue;
    }

    @Override
    public String toString() {
        return "IDRACCardStringView{" +
                "attributeDisplayName='" + attributeDisplayName + '\'' +
                ", attributeName='" + attributeName + '\'' +
                ", currentValue='" + currentValue + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", dependency='" + dependency + '\'' +
                ", displayOrder='" + displayOrder + '\'' +
                ", fQDD='" + fQDD + '\'' +
                ", groupDisplayName='" + groupDisplayName + '\'' +
                ", groupID='" + groupID + '\'' +
                ", instanceID='" + instanceID + '\'' +
                ", isReadOnly='" + isReadOnly + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", minLength='" + minLength + '\'' +
                ", pendingValue='" + pendingValue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IDRACCardStringView that = (IDRACCardStringView) o;

        if (attributeDisplayName != null ? !attributeDisplayName.equals(that.attributeDisplayName) : that.attributeDisplayName != null) {
            return false;
        }
        if (attributeName != null ? !attributeName.equals(that.attributeName) : that.attributeName != null) {
            return false;
        }
        if (currentValue != null ? !currentValue.equals(that.currentValue) : that.currentValue != null) {
            return false;
        }
        if (defaultValue != null ? !defaultValue.equals(that.defaultValue) : that.defaultValue != null) {
            return false;
        }
        if (dependency != null ? !dependency.equals(that.dependency) : that.dependency != null) {
            return false;
        }
        if (displayOrder != null ? !displayOrder.equals(that.displayOrder) : that.displayOrder != null) {
            return false;
        }
        if (fQDD != null ? !fQDD.equals(that.fQDD) : that.fQDD != null) {
            return false;
        }
        if (groupDisplayName != null ? !groupDisplayName.equals(that.groupDisplayName) : that.groupDisplayName != null) {
            return false;
        }
        if (groupID != null ? !groupID.equals(that.groupID) : that.groupID != null) {
            return false;
        }
        if (instanceID != null ? !instanceID.equals(that.instanceID) : that.instanceID != null) {
            return false;
        }
        if (isReadOnly != null ? !isReadOnly.equals(that.isReadOnly) : that.isReadOnly != null) {
            return false;
        }
        if (maxLength != null ? !maxLength.equals(that.maxLength) : that.maxLength != null) {
            return false;
        }
        if (minLength != null ? !minLength.equals(that.minLength) : that.minLength != null) {
            return false;
        }
        if (pendingValue != null ? !pendingValue.equals(that.pendingValue) : that.pendingValue != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = attributeDisplayName != null ? attributeDisplayName.hashCode() : 0;
        result = 31 * result + (attributeName != null ? attributeName.hashCode() : 0);
        result = 31 * result + (currentValue != null ? currentValue.hashCode() : 0);
        result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
        result = 31 * result + (dependency != null ? dependency.hashCode() : 0);
        result = 31 * result + (displayOrder != null ? displayOrder.hashCode() : 0);
        result = 31 * result + (fQDD != null ? fQDD.hashCode() : 0);
        result = 31 * result + (groupDisplayName != null ? groupDisplayName.hashCode() : 0);
        result = 31 * result + (groupID != null ? groupID.hashCode() : 0);
        result = 31 * result + (instanceID != null ? instanceID.hashCode() : 0);
        result = 31 * result + (isReadOnly != null ? isReadOnly.hashCode() : 0);
        result = 31 * result + (maxLength != null ? maxLength.hashCode() : 0);
        result = 31 * result + (minLength != null ? minLength.hashCode() : 0);
        result = 31 * result + (pendingValue != null ? pendingValue.hashCode() : 0);
        return result;
    }
}
