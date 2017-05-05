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

    public enum WSManClassEnum {

        DCIM_SoftwareIdentity, DCIM_SoftwareInstallationService, DCIM_SoftwareUpdateConcreteJob, DCIM_SystemView, DCIM_ControllerView, DCIM_VirtualDiskView, DCIM_PhysicalDiskView, DCIM_BIOSEnumeration, DCIM_BootSourceSetting, DCIM_BootConfigSetting, DCIM_BIOSString, DCIM_BIOSInteger, DCIM_NICView, DCIM_NICString, DCIM_NICInteger, DCIM_NICEnumeration, DCIM_FCView, DCIM_FCString, DCIM_FCInteger, DCIM_FCEnumeration, DCIM_LifecycleJob, DCIM_JobService, InstallFromURI, CreateRebootJob, SetupJobQueue, DCIM_ComputerSystem, DCIM_OSDeploymentService, DCIM_BIOSService, DCIM_OEM_DataAccessModule, DCIM_RAIDService, CIM_ComputerSystem, DCIM_LCService, DCIM_IDRACCardView, DCIM_SPComputerSystem, CIM_IPProtocolEndpoint, CIM_Chassis, CIM_SoftwareIdentity, CIM_InstalledSoftwareIdentity, DCIM_Memoryview, DCIM_Powersupplyview, DCIM_PCIDeviceView, DCIM_CPUView, DCIM_EnclosureView, DCIM_iDRACCardAttribute, DCIM_PSNumericsensor, DCIM_iDRACCardService, DCIM_iDRACCardString, DCIM_iDRACCardEnumeration, DCIM_NICStatistics, DCIM_BaseMetricValue, DCIM_AggregationMetricValue, CIM_Account, // generic
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    // account
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    // service
        DCIM_MFAAccount, // 11g account service
        DCIM_Account, // 12g account service
        DCIM_LicenseManagementService, DCIM_LicensableDevice, DCIM_SelRecordLog, DCIM_License, DCIM_SystemManagementService, DCIM_BladeServerView, DCIM_ModularChassisView, DCIM_SELLogEntry, DCIM_CSPowerManagementService;
    }

    public enum WSManEnumerationMode {
        EnumerateObject, EnumerateEPR, EnumerateObjectAndEPR;
    }

    public enum WSManInvokableEnum {
        DCIM_LicenseManagementService(WSManClassEnum.DCIM_LicenseManagementService, "DCIM_SPComputerSystem", "systemmc", "DCIM:LicenseManagementService:1", "License Management Service"), DCIM_iDRACCardService(WSManClassEnum.DCIM_iDRACCardService, "DCIM_ComputerSystem", "DCIM:ComputerSystem", "DCIM:iDRACCardService", "DRAC Service"), DCIM_SystemManagementService(WSManClassEnum.DCIM_SystemManagementService, "DCIM_ComputerSystem", "srv:system", "DCIM:SystemManagementService", "System Management Service"), DCIM_LCService(WSManClassEnum.DCIM_LCService, "DCIM_ComputerSystem", "DCIM:ComputerSystem", "DCIM:LCService", "LC Service"), DCIM_SoftwareInstallationService(WSManClassEnum.DCIM_SoftwareInstallationService, WSManClassEnum.DCIM_ComputerSystem.toString(), "IDRAC:ID", "SoftwareUpdate", "Software Installation Service"),

        DCIM_PowerBootService(WSManClassEnum.DCIM_ComputerSystem, null, null, "srv:system", "Power Boot Service"),
        // DCIM_SoftwareInstallationService(WSManClassEnum.DCIM_SoftwareInstallationService,
        // "DCIM_SoftwareInstallationService", "DCIM:ComputerSystem", "DCIM:SoftwareInstallationService", "Software Installation Service"),

        DCIM_JobService(WSManClassEnum.DCIM_JobService, WSManClassEnum.DCIM_ComputerSystem.toString(), WSCommandRNDConstant.SYSTEM_NAME, WSCommandRNDConstant.JOB_SERVICE, "Job Service"), DCIM_CSPowerManagementService(WSManClassEnum.DCIM_CSPowerManagementService, "DCIM_SPComputerSystem", "systemmc", "pwrmgtsvc:1", "Power Management Service"), DCIM_SelRecordLog(WSManClassEnum.DCIM_SelRecordLog, WSManClassEnum.DCIM_ComputerSystem.toString(), WSCommandRNDConstant.SYSTEM_NAME, "SelRecordLogService", "SEL Record Log Service");
        private final WSManClassEnum cmdEnum;
        private final String systemCreationClassName;
        private final String creationClassName;
        private final String systemName;
        private final String name;
        private final String elementName;


        private WSManInvokableEnum(WSManClassEnum cmdEnum, String systemCreationClassName, String systemName, String name, String elementName) {
            this.cmdEnum = cmdEnum;
            this.systemCreationClassName = systemCreationClassName;
            this.creationClassName = cmdEnum.name();
            this.systemName = systemName;
            this.name = name;
            this.elementName = elementName;
        }


        public WSManClassEnum getCommandEnum() {
            return cmdEnum;
        }


        public String getSystemCreationClassName() {
            return systemCreationClassName;
        }


        public String getCreationClassName() {
            return creationClassName;
        }


        public String getSystemName() {
            return systemName;
        }


        public String getName() {
            return name;
        }


        public String getElementName() {
            return elementName;
        }
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

    public enum WSManMethodEnum {
        DRIVER_PACK_INFO("GetDriverPackInfo"), HOST_MAC_INFO("GetHostMACInfo"), BOOT_TO_PXE("BootToPXE"), BOOT_REMOTE_ISO("BootToNetworkISO"), UNPACK_AND_ATTACH("UnpackAndAttach"), DETACH_ISO("DetachISOImage"), DETACH_DRIVERS("DetachDrivers"), DETACH_ISO_VFLASH("DetachISOFromVFlash"), DELETE_ISO_VFLASH("DeleteISOFromVFlash"), DOWNLOAD_ISO_VFLASH("DownloadISOToVFlash"), BOOT_ISO_VFLASH("BootToISOFromVFlash"), CREATE_REBOOT_JOB("CreateRebootJob"), DELETE_JOB_QUEUE("DeleteJobQueue"), SETUP_JOB_QUEUE("SetupJobQueue"), INSTALL_FROM_URI("InstallFromURI"), CHANGE_BOOT_ORDER("ChangeBootOrderByInstanceID"), CHANGE_BOOT_STATUS("ChangeBootSourceState"), CREATE_TARGET_CONFIG_JOB("CreateTargetedConfigJob"), CREATE_LC_SERVICE_CONFIG_JOB("CreateConfigJob"), SET_ATTRIBUTE("SetAttribute"), SET_ATTRIBUTES("SetAttributes"), SEND_CMD("SendCmd"), REQUESTED_STATE_CHANGE("RequestStateChange"), REQUESTED_POWER_STATE_CHANGE("RequestPowerStateChange"), REINITIATE_DHS("ReInitiateDHS"), GET_RS_STATUS("GetRSStatus"), GET_NETWORK_ISO_IMAGE_CONNECTION_INFO("GetNetworkISOImageConnectionInfo"), CONNECT_TO_NEWORK_ISO_IMAGE("ConnectNetworkISOImage"), DISCONNECT_NETWORK_ISO_IMAGE("DisconnectNetworkISOImage"), EXPORT_LICENSE("ExportLicense"), CLEAR_SEL_LOG("ClearLog"), BLINK_LED("IdentifyChassis");
        String enumValue;


        WSManMethodEnum(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }

    }
}
