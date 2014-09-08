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
import java.util.TimeZone;

/**
 *
 * @author mat
 */
public class GitDateFormat {

    private static final TimeZone tz = TimeZone.getTimeZone("UTC");
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static {
        df.setTimeZone(tz);
    }

    /**
     * Parses dates that are in yyyy-MM-dd'T'HH:mm:ss'Z' format into a Date
     * object
     *
     * @param date
     * @return
     */
    public static Date parse(String date) {
        try {
            return df.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Formats date object in the string format of yyyy-MM-dd'T'HH:mm:ss'Z'
     *
     * @param date
     * @return A date in the format of yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String format(Date date) {
        return df.format(date);
    }

    /**
     * Private constructor, can't initialize object
     */
    private GitDateFormat() {
    }
}
