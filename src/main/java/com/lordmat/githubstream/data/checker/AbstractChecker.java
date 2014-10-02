/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data.checker;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A thread that calls the query method and then sleeps for the time passed in.
 * The sleep time will always be over 20 seconds
 *
 * @author mat
 */
public abstract class AbstractChecker extends Thread {

    private final static Logger LOGGER = Logger.getLogger(AbstractChecker.class.getName());

    /**
     * How long before checking for new commits
     */
    private int queryTime;

    /**
     *
     * @param queryTime If less than 20 seconds, will be set to 20 seconds
     */
    public AbstractChecker(int queryTime) {
        this.queryTime = queryTime < 20_000 ? 20_000 : queryTime;
    }

    @Override
    public void run() {
        while (true) {
            query();

            try {
                sleep(queryTime);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.FINE, this.getClass() + " interrupted", ex);
            }

        }
    }

    protected abstract void query();
    
    /**
     * Sets the query time in seconds
     * @param queryTime 
     */
    public void setQueryTime(int queryTime) {
        queryTime *= 1000;
        this.queryTime = queryTime < 20_000 ? 20_000 : queryTime;
    }
}
