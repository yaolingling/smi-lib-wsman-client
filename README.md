### smi-lib-wsman-client

A common lib for Dell Server management via out of band management controller.  The library is written in Java language to support Webservice Management - WSMAN protocol, a DMTF standard protocol for systems management.

The smi-lib-wsman has factory classes that establish a connection over the https protocol to the target compute node exposing standard SOAP based webservices for management and monitoring.  

Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved. 
 
### How to Install 

Under Construction. Not available via Maven Central yet.... 

##### Maven Example:
~~~
<dependency>
    <groupId>com.dell.isg.smi</groupId>
    <artifactId>wsmanclient</artifactId>
    <version>1.0.1</version>
</dependency>
~~~

##### Gradle Example:
~~~
compile(group: 'com.dell.isg.smi', name: 'wsmanclient', version: '1.0.1')
~~~  
  
### API Usage 
Once Jar is downloaded or built from source code, the lib is ready for use e.g. 

        
        DcimEnumerationCmd raidEnumCmd =null;
        
        raidEnumCmd = new DcimEnumerationCmd(ipAddress, userName(), password, WSManClassEnum.DCIM_RAIDEnumeration.name());
        
        List<DcimEnumCmdView> raidEnumViews = raidEnumCmd.execute();
        
        DcimIntegerCmd raidIntegerCmd = new DcimIntegerCmd((ipAddress, userName(), password, WSManClassEnum.DCIM_RAIDInteger.name());
        
        List<DcimIntegerCmdView> raidIntegerViews = raidIntegerCmd.execute();
         
         if (raidIntegerViews!=null && raidIntegerViews.size()>0){
         
            //custom logic
          
         }
        
 
### Setup Development Environment 

  1- Developer can check out the source code from github into IDE of choice and start contributing to the project.
  

  2- Download dependencies 
     
       https://github.com/rackhd-epf/wsmanlib.git
      
    Alternativly dependencies can be downloaded from artifactory
    
     https://gtie-artifactory.us.dell.com/artifactory/libs-release-local)
    
  3- Install and configure latest Gradle build tool
  
  4- Go to the project root directory and run
     
      gradle build

   This build script will download the dependencies from central maven and start building liberary  
   
### Licensing

Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

RackHD is a Trademark of Dell EMC

### Support

Please file bugs and issues at the GitHub issues page. The code and documentation are released with no warranties or SLAs and are intended to be supported through a community driven process.

Slack Channel: codecommunity.slack.com



