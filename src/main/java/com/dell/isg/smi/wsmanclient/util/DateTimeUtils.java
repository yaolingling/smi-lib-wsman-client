/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsmanclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateTimeUtils {
    private DateTimeUtils() {
    }

    private static final Logger _logger = LoggerFactory.getLogger(DateTimeUtils.class);

    // Compile the pattern used to normalize data strings once. This pattern
    // splits on (1) the dot after seconds number and (2) the plus or minus
    // sign before the timezone number.
    private static final Pattern normalizeTsPattern = Pattern.compile("\\.|\\-|\\+");


    /**
     * Transform a date in a long to a GregorianCalendar
     *
     * @param date as a long value
     * @return Transformed date in a long format to a GregorianCalendar
     */
    public static XMLGregorianCalendar long2Gregorian(long date) {
        DatatypeFactory dataTypeFactory;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date);
        return dataTypeFactory.newXMLGregorianCalendar(gc);
    }


    /**
     * Transform a date in Date to XMLGregorianCalendar
     * 
     * @param date as a GregorianCalendar value
     * @return Transformed date from a GregorianCalendar format to a long
     */
    public static XMLGregorianCalendar date2Gregorian(Date date) {
        return (date == null) ? null : long2Gregorian(date.getTime());
    }


    /**
     * Returns XMLGregorianCalendar with given date set as UTC date. This method simply sets the timezone on the passed in date to UTC. It does not recalculate the date. For
     * example passing in 10th Aug 2010 14:30 CDT will return 10th Aug 2010 14:30 UTC.
     *
     * @param date as a Date class instance
     * @return Transformed date in a Date format to a GregorianCalendar
     */
    public static XMLGregorianCalendar date2UTCGregorian(Date date) {
        if (date == null)
            return null;
        XMLGregorianCalendar xcal = long2Gregorian(date.getTime());
        xcal.setTimezone(0);
        return xcal;
    }


    /**
     * Returns the number of milliseconds in a time interval specified in days,minutes and seconds.
     *
     * @param days the number of days in the interval
     * @param hours the number of hours in the interval
     * @param minutes the number of minutes in the interval
     * @param seconds the number of seconds in the interval
     * @return number of milliseconds
     */
    public static long getMillisecondsInInterval(int days, int hours, int minutes, int seconds) {
        return ((days * 86400) + (hours * 3600) + (minutes * 60) + seconds) * 1000;
    }


    /**
     * Returns the first element of a collection if collection is not empty, else returns null.
     *
     * @param <T> collection
     * @param collection of elements
     * @return the first element of a collection if collection is not empty, else returns null.
     */
    public static <T> T getFirstElement(Collection<T> collection) {
        return (collection == null || collection.isEmpty()) ? null : (T) collection.iterator().next();
    }


    /**
     * Returns current UTC date.
     *
     * @return current UTC date
     */
    public static Date getUTCDate() {
        return getUTCDate(new Date());
    }


    /**
     * Returns the UTC date corresponding to the non UTC date passed in.
     *
     * @param nonUTCDate in Date class format
     * @return the UTC date corresponding to the non UTC date passed in
     */
    public static Date getUTCDate(Date nonUTCDate) {
        if (nonUTCDate != null) {
            int timeZoneOffset = TimeZone.getDefault().getOffset(nonUTCDate.getTime());
            Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            utcCal.setTimeInMillis((nonUTCDate.getTime() - timeZoneOffset));
            return utcCal.getTime();
        }
        return null;
    }


    /**
     * Returns the UTC date corresponding to the non UTC date passed in.
     *
     * @param nonUTCalendar in XMLGregorianCalendar class format
     * @return the UTC date corresponding to the non UTC date passed in.
     */
    public static XMLGregorianCalendar getUTCXMLGregorianCalendar(XMLGregorianCalendar nonUTCalendar) {
        if (nonUTCalendar != null) {
            int timeZoneOffset = TimeZone.getDefault().getOffset(nonUTCalendar.toGregorianCalendar().getTimeInMillis());
            GregorianCalendar utcCal = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
            utcCal.setTimeInMillis((nonUTCalendar.toGregorianCalendar().getTimeInMillis() - Math.abs(timeZoneOffset)));
            XMLGregorianCalendar xgcal;
            try {
                xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(utcCal);
                return xgcal;
            } catch (DatatypeConfigurationException e) {
                _logger.debug("Exception swallowed when converting the Non UTC date to the corresponding UTC date:" + e.getMessage());
            }
        }
        return null;
    }


    public static String asDateTimeString() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ROOT);
        return formatter.format(date).trim();
    }

    static final String[] datePatterns = { "EEE MMM dd HH:mm:ss yyyy z", "EEE MMM dd HH:mm:ss z yyyy", "EEE MMM dd HH:mm:ss yyyy", "MMM dd, yyyy", "MMM dd yyyy", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyyMMddHHmmss" };


    /*
     * This method will try to form the java.util.Date object from the string representation of the date. The date patterns specified in the datePatterns will be used to see the
     * best fit.
     * 
     * @param dateString: The string date provided is assumed to be in UTC timezone.
     * 
     * @return Date if successful else null if format doesn't match any date patterns.
     */
    public static Date getCorrespondingUTCDate(String dateString) {
        Date dt = null;
        if (dateString == null) {
            return null;
        }
        for (String datePattern : datePatterns) {
            dt = getUTCDateFromString(datePattern, dateString);
            if (dt != null)
                break;
        }
        return dt;
    }


    public static Long getEpochTime(String dateString) {
        Date date = getCorrespondingUTCDate(dateString);
        if (null != date) {
            return date.getTime() / 1000;
        }
        return null;
    }


    public static XMLGregorianCalendar getGregorianDateFromString(String dateString) {
        Date dt = null;
        if (dateString == null) {
            return null;
        }
        for (String datePattern : datePatterns) {
            dt = getUTCDateFromString(datePattern, dateString);
            if (dt != null)
                break;
        }
        return date2Gregorian(dt);
    }


    private static Date getUTCDateFromString(String pattern, String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        formatter.setLenient(false);
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }


    /* ------------------------------------------------------------------------- */
    /* normalizeTimeStamp: */
    /* ------------------------------------------------------------------------- */

    /**
     * Normalize a timestamp so that it conforms to the date format yyyyMMddHHmmss.SSSZ as defined by the SimpleDateFormat class. This normalization is often needed when we get
     * timestamps back from a device.
     *
     * @param ts - the timestamp to be normalized.
     * @return the normalized timestamp.
     */
    public static String normalizeTimeStamp(String ts) {
        // Garbage in...
        if ((ts == null) || ts.isEmpty())
            return ts;
        String result = "";

        // Split string into three components:
        //
        // yyyyMMddHHmmss followed by "."
        // microseconds of length 6 followed by + or -
        // numerical timezone of length 4
        //
        String[] parts = normalizeTsPattern.split(ts);
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                // Make first part conform to year through seconds portion.
                parts[i] = appendToString(parts[i], '0', "yyyyMMddHHmmss".length());
                result += parts[i];
            } else if (i == 1) {
                // Java Dates cannot handle precision beyond milliseconds, but the values we get
                // from iDrac can have microseconds (or beyond?).

                // Zero pad to one place more than milliseconds
                String digitsStr = (parts[i].length() < 4) ? appendToString(parts[i], '0', 4) : parts[i].substring(0, 4);
                int digits = Integer.valueOf(digitsStr);

                // Round to millis
                int millis = Math.round(digits / 10.f);
                String millisStr = Integer.toString(millis);
                result += "." + prependToStr(millisStr, '0', 3);
            } else if (i == 2) {
                // Make third part conform to a fixed length timezone number.
                parts[i] = prependToStr(parts[i], '0', 4);
                result += (ts.contains("+") ? "+" : "-");
                result += parts[i];
            }
        }
        return result;
    }


    /* ------------------------------------------------------------------------- */
    /* appendToString: */
    /* ------------------------------------------------------------------------- */
    private static String appendToString(String s, char c, int finalStringLen) {
        String tmpStr = s;
        while (tmpStr.length() < finalStringLen)
            tmpStr += c;
        return tmpStr;
    }


    /* ------------------------------------------------------------------------- */
    /* prependToStr: */
    /* ------------------------------------------------------------------------- */
    private static String prependToStr(String s, char c, int finalStringLen) {
        String tmpStr = s;
        while (tmpStr.length() < finalStringLen)
            tmpStr = c + tmpStr;
        return tmpStr;
    }

}
