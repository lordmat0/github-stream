/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.resource;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles resource access to project.properties using Enumerated
 * types for type safety
 *
 * @author mat
 */
public class MyResourceBundle {

    private final static Logger LOGGER = Logger.getLogger(MyResourceBundle.class.getName());

    private static ResourceBundle bundle;

    static {
        try {
            bundle = ResourceBundle.getBundle("project");
        } catch (Exception ex) {
            String errorMessage = "Could not find bundle, "
                    + "make sure project.properties is in the class path";

            LOGGER.log(Level.SEVERE, errorMessage, ex);

            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * Private constructor, can't make instance of class
     */
    private MyResourceBundle() {
    }

    /**
     * Checks project.properties for key
     *
     * @param key ResourceKey to check for
     * @return value matched up to the key
     */
    public static String getString(ResourceKey key) {
        try {
            return bundle.getString(key.name());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Could not find resource "
                    + "from bundle, Expected: " + key.name(), ex);
            return null;
        }
    }
}
