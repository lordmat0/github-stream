/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.util;

import com.lordmat.githubstream.api.GitHubCaller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mat
 */
public class GitDateFormat {

    private final static Logger LOGGER = Logger.getLogger(GitDateFormat.class.getName());

    private static final TimeZone tz = TimeZone.getTimeZone("UTC");
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static {
        df.setTimeZone(tz);
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
            return df.parse(dateString);
        } catch (ParseException ex) {
            LOGGER.log(Level.WARNING, "Parse Exception of date", ex);
            return null;
        }
    }

    /**
     * Formats date object in the string format of yyyy-MM-dd'T'HH:mm:ss'Z', ,
     * returns null if fails
     *
     * @param date The date to be turned into a string
     * @return A date in the format of yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String format(Date date) {
        try {
            return df.format(date);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING,
                    "Exception occured trying to format date", ex);
            return null;
        }
    }

    /**
     * Private constructor, can't initialize object
     */
    private GitDateFormat() {
    }
}
