package com.lordmat.githubstream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author mat
 */
public class StartManager implements ServletContextListener {

    private static GitHubData gitHubData;
    
    public StartManager(){
        if(gitHubData != null){
            gitHubData = new GitHubData();
        }
    }
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Program starts here
        System.out.println("contextInitialized");
        
    }
    
    public static GitHubData getData(){
        return gitHubData;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("contextDestroyed");
    }

}
