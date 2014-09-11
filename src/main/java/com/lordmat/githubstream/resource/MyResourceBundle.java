/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.resource;

import java.util.ResourceBundle;

/**
 *
 * @author mat
 */
public class MyResourceBundle {

    private static ResourceBundle bundle;

    static {
        try {
            bundle = ResourceBundle.getBundle("project");
        } catch (Exception ex) {
            throw new RuntimeException("Could not find bundle, "
                    + "make sure project.properties is in class path");
        }
    }

    private MyResourceBundle() {
    }

    public static String getString(String key) {
        //TODO logging
        return bundle.getString(key);
    }
}
