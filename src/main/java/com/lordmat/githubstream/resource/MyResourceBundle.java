/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.resource;

import com.lordmat.githubstream.api.GitHubCaller;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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

    private MyResourceBundle() {
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Could not find resource from bundle", ex);
            return null;
        }
    }
}
