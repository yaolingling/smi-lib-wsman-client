/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient;

public class WSCommandRNDConstant {
    static final String IPL = "IPL";
    static final String UEFI = "UEFI";
    static final String VFLASH = "vFlash";
    static final String BCV = "BCV";
    static final String BIOS = "BIOS";
    static final String BIOS_IN_SYSTEM = "Bios";
    static final String UEFI_IN_SYSTEM = "Uefi";

    static final String defaultPrefix = "//n1:";
    static final String baseuri = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/";
    public static final String osdsvcdellbaseuri = "http://schemas.dell.com/wbem/wscim/1/cim-schema/2/";
    public static final String NameSpaceWSMan = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd";
    public static final String AddressingWSMan = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
    static final String osdsvcclassname = "DCIM_OSDeploymentService";
    static final String osdsvcnamespace = "root/dcim/";
    static final String unpackinterval = "000000180000:000000:000";
    static final String vflashexposeduration = "00000000180000.000000:000";
    // All key value params required for getting IDrac Subnet Mask -->
    static final String idracsubnetclass = "DCIM_IPProtocolEndpoint";
    public static final String APPLY_ATTRIBUTES = "ApplyAttributes";
    public static final String SET_ATTRIBUTES = "SetAttributes";
    public static final String DCIM_iDRAC_CARD_SERVICE = "DCIM_iDRACCardService";
    public static final String DCIM_LC_SERVICE = "DCIM_LCService";
    public static final String GetRemoteServicesAPIStatus = "GetRemoteServicesAPIStatus";
    static final String idracsubnetclassnmsp = "root/dcim/";
    // All key value params required for getting IDrac Mac ID -->
    static final String idracmacidclass = "DCIM_EthernetPort";
    static final String idracmacidclassnmsp = "root/dcim/";
    // All key value params required for Idrac Account Info -->
    static final String idracacctclass = "CIM_Account";
    static final String idracacctclassnmsp = "root/dcim/";
    // Key value params for host service tag information
    static final String hostsvctagclass = "CIM_ComputerSystem";
    static final String hostsvctagclassnmsp = "root/dcim/";
    static final int timeInterval = 7000; // in ms
    static final String JobArray = "JobArray";
    static final String StartTimeInterval = "StartTimeInterval";
    public static final String WSMAN_BASE_URI = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/";
    public static final String WS_OS_SVC_NAMESPACE = "root/dcim/";
    public static final String WS_MAN_NAMESPACE = "http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd";
    public static final String SOAP_NAMESPACE = "http://www.w3.org/2003/05/soap-envelope";
    public static final String WSMAN_ITEMS_TAG = "Items";
    public static final String SOAP_BODY_TAG = "Body";
    public static final String SOFTWARE_IDENTITY_ITEMS_TAG = "Items";
    static final String BOOTSOURCE_BIOS_BOOT_STRING = "BIOSBootString";
    static final String BOOTSOURCE_BOOT_STRING = "BootString";
    static final String BOOTSOURCE_INSTANCE_ID = "InstanceID";
    static final String BOOTSOURCE_CURRENT_ASSIGNED_SEQ = "CurrentAssignedSequence";
    static final String BOOTSOURCE_CURRENT_ENABLED_STATUS = "CurrentEnabledStatus";
    static final String BOOTSOURCE_ELEMENT_NAME = "ElementName";
    static final String BOOTSOURCE_FAIL_THROUGH_SUPPORTED = "FailThroughSupported";
    static final String BOOTSOURCE_PENDING_ENABLED_SEQ = "PendingAssignedSequence";
    static final String BOOTSOURCE_PENDING_ENABLED_STATUS = "PendingEnabledStatus";
    public static final String BOOTSOURCE_HARDDISK = "IPL:HardDisk";
    static final String DCIM_iDRACCardEnumeration = "DCIM_iDRACCardEnumeration";

    public static final String BOOTCONFIG_ELEMENT_NAME = "ElementName";
    public static final String BOOTCONFIG_INSTANCE_ID = "InstanceID";
    public static final String INSTANCE_ID = "InstanceID";
    public static final String SOFTWARE_IDENTITY = "DCIM_SoftwareIdentity";
    public static final String BOOTCONFIG_IS_CURRENT = "IsCurrent";
    public static final String BOOTCONFIG_IS_DEFAULT = "IsDefault";
    public static final String BOOTCONFIG_IS_NEXT = "IsNext";
    // Targeted Config Jobs
    public static final String TARGET_iDRAC_CONFIG_JOB = "iDRAC.Embedded.1";
    // RAID CONFIG
    public static final String RAID_SERVICE_URI = "DCIM_RAIDService";

    // Software Installation Service
    public static final String SOFTWARE_INSTALLATION_SERVICE_URI = "DCIM_SoftwareInstallationService";

    public static final String IS_CURRENT = "1";
    public static final int CURRENT_ENABLED_STATUS = 1;
    public static final int CURRENT_DISABLED_STATUS = 0;
    public static final String SUCCESSFULL_CONFIG_JOB_RETURN = "4096";
    public static final String COMPLETED_WITH_NO_ERROR = "0";
    public static final String NOT_SUPPORTED = "1";
    public static final String ERROR = "2";

    // iDrac Credentials
    public final String DISABLE = "3";
    public final String ENABLE = "2";
    public final String INDEFINITE_TIMEOUT_PERIOD = "NULL";
    public static final String SUCCESSFULL_UPDATE_JOB_RETURN = "0";
    public static final String SYSTEM_NAME = "Idrac";
    public static final String JOB_SERVICE = "JobService";
    public static final String RESET_IDRAC = "11";
    public static final String SHUTDOWN_IDRAC = "3";
    public static final String Management_Controller = "14";
}
