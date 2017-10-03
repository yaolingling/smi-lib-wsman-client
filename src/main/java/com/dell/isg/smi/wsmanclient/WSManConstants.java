/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

public final class WSManConstants {
    private WSManConstants() {
    }

    public static final String WSMAN_ENUMERATE_URI = "http://schemas.xmlsoap.org/ws/2004/09/enumeration/Enumerate";
    public static final String WSMAN_GET_URI = "http://schemas.xmlsoap.org/ws/2004/09/transfer/Get";
    public static final String WSMAN_PUT_URI = "http://schemas.xmlsoap.org/ws/2004/09/transfer/Put";


    public enum WSManEnumerationMode {
        EnumerateObject, EnumerateEPR, EnumerateObjectAndEPR;
    }
    
    public enum WSManMethodParamEnum {

        INSTANCE_ID("InstanceID"), SOURCE("source"), ENABLED_STATE("EnabledState"), REBOOT_JOB_TYPE("RebootJobType"), SCHEDULED_START_TIME("ScheduledStartTime"), SYSTEM_CLASS_NAME("SystemCreationClassName"), CREATION_CLASS_NAME("CreationClassName"), SYSTEM_NAME("SystemName"), NAME("Name"), TARGET("Target"), REBOOT_IF_REQUIRED("RebootIfRequired"), REQUESTED_STATE("RequestedState"), TIME_OUT_PERIOD("TimeoutPeriod"), ATTRIBUTE_NAME("AttributeName"), ATTRIBUTE_VALUE("AttributeValue"), PROVISIONING_SERVER("ProvisioningServer"), RESET_TO_FACTORY_DEFAULTS("ResetToFactoryDefaults"), PERFORM_AUTO_DISCOVERY("PerformAutoDiscovery");

        String enumValue;


        WSManMethodParamEnum(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

}
