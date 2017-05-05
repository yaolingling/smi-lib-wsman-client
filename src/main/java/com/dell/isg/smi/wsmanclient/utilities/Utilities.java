/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *
 */
public class Utilities {
    private final static Logger log = LoggerFactory.getLogger(Utilities.class);


    public static void closeStreamQuietly(Closeable cs) {
        if (cs != null) {
            try {
                cs.close();
            } catch (NullPointerException e) {
                log.warn("Can't close stream");
            } catch (RuntimeException e) {
                log.warn("Can't close stream");
            } catch (Exception e) {
                log.warn("Can't close stream");
            } catch (Throwable ex) {
                log.warn("Can't close stream");
            }
        }
    }


    public static int getTimeZoneOffset() {
        TimeZone tz = TimeZone.getDefault();
        Calendar cal = Calendar.getInstance();
        return tz.getOffset(cal.getTimeInMillis()) / 1000 / 60;
    }


    public static XMLGregorianCalendar getXmlDateTimeWithCurrentTimeZone(XMLGregorianCalendar date) {

        DatatypeFactory df;
        try {
            // Get the current date/calendar
            df = DatatypeFactory.newInstance();
            XMLGregorianCalendar xmlCalendar = df.newXMLGregorianCalendar(date.getYear(), date.getMonth(), date.getDay(), date.getHour(), date.getMinute(), date.getSecond(), 0, getTimeZoneOffset());
            return xmlCalendar;

        } catch (DatatypeConfigurationException e) {

        }
        return null;
    }


    public static String readFileAsString(String filePath) throws IOException {
        StringBuilder file = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String strLine = null;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                file.append(strLine);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return file.toString();
    }


    public static void writeFileAsString(String filePathToSave, String fileContents) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        File file = null;
        try {
            if (fileContents != null) {
                file = new File(filePathToSave);
                fw = new FileWriter(file, false);
                if (file.canWrite()) {
                    bw = new BufferedWriter(fw);
                    bw.write(fileContents, 0, fileContents.length());
                    bw.flush();
                } else {
                    throw new IOException("Unable to open config file for writing");
                }
            }
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ex) {
                    log.debug(ex.getMessage());
                }
            }

            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    log.debug(ex.getMessage());
                }
            }
        }

    }


    public static String getFileName(String filePath) throws MalformedURLException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new MalformedURLException();
        }

        String path = filePath.replace("\\", "/");
        String[] pathValues = path.split("/");
        String fileName = "";
        if (pathValues.length > 0) {
            fileName = pathValues[pathValues.length - 1];
            if (fileName.contains(".")) {
                String[] values = fileName.split("\\.");
                if (values.length >= 2) {
                    for (int i = 0; i < values.length; i++) {
                        if (values[i].length() == 0) {
                            fileName = "";
                            break;
                        }
                    }
                }
            } else {
                fileName = "";
            }
        }

        if (!fileName.isEmpty()) {
            return fileName;
        }
        throw new MalformedURLException(filePath);

    }

}
