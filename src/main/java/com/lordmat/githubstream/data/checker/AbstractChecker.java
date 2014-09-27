/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.data.GitHubCaller;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mat
 */
public abstract class AbstractChecker extends Thread {
    
    private final static Logger LOGGER = Logger.getLogger(AbstractChecker.class.getName());
    
    /**
     * How long before checking for new commits
     */
    private final int QUERY_TIME;

    
    public AbstractChecker(int queryTime){
        QUERY_TIME = queryTime;
    }
    
        @Override
    public void run() {
        while (true) {
            query();

            try {
                sleep(QUERY_TIME);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.FINE, "CommitChecker interrupted", ex);
            }

        }
    }

    protected abstract void query();
}
