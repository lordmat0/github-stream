/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles commonly used date/time parsing and formatting in this project
 *
 * @author mat
 */
public class DateTimeFormat {

    private final static Logger LOGGER = Logger.getLogger(DateTimeFormat.class.getName());

    private static final DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final DateFormat time = new SimpleDateFormat("HH:mm:ss");
   
    
    static{
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        //dateTime.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Parses dates that are in yyyy-MM-dd'T'HH:mm:ss'Z' format into a Date
     * object, returns null if fails
     *
     * @param dateString The string to be converted
     * @return A date from the string given or null
     */
    public static Date parse(String dateString) {
        try {
            return dateTime.parse(dateString);
        } catch (ParseException ex) {
            LOGGER.log(Level.WARNING, "Parse Exception of date", ex);
            return null;
        }
    }

    /**
     * Formats date object in the string format of yyyy-MM-dd'T'HH:mm:ss'Z',
     * returns null if fails
     *
     * @param date The date to be turned into a string
     * @return A date in the format of yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String format(Date date) {
        try {
            return dateTime.format(date);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING,
                    "Exception occured trying to format date", ex);
            return null;
        }
    }

    /**
     * Formats date object in the string format of HH:mm:ss'Z', returns null if
     * fails
     *
     * @param date The date to be turned into a string
     * @return A date in the format of yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String getTime(Date date) {
        return time.format(date);
    }

    /**
     * Private constructor, can't initialize object
     */
    private DateTimeFormat() {
    }
}
