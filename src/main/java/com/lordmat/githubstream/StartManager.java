package com.lordmat.githubstream;

import com.lordmat.githubstream.data.GitHubData;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author mat
 */
public class StartManager implements ServletContextListener {

    public StartManager() {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Program starts here
        System.out.println("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("contextDestroyed");
    }

}
