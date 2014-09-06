/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.web;

import com.lordmat.githubstream.api.GitHubAPI;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author mat
 */
public class StartManager implements ServletContextListener {

    public static GitHubAPI gitHubAPI;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Program starts here
        System.out.println("contextInitialized");
        gitHubAPI = new GitHubAPI();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("contextDestroyed");
    }

}
