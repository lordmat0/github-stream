/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import com.lordmat.githubstream.util.GitDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mat
 */
public class CommitChecker extends Thread {

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits) {
        this.gitHubCommits = gitHubCommits;
        caller = new GitHubCaller();
    }

    @Override
    public void run() {
        while (true) {
            queryGitHub();

            try {
                sleep(10000);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.FINE, "CommitChecker interrupted", ex);
            }

        }
    }
    
    private void queryGitHub() {
        try {
            String since = null;
            if (!gitHubCommits.isEmpty()) {

                Calendar cal = Calendar.getInstance();
                cal.setTime(gitHubCommits.lastKey());

                // Add one second otherwise github returns the commit that happened on that date
                cal.add(Calendar.SECOND, 1);

                since = GitDateFormat.format(cal.getTime());
            }

            Map<Date, GitHubCommit> data = caller.getCommits(since, null);

            gitHubCommits.putAll(data);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CommitChecker threw an error, re-trying", ex);
        }
    }

}
