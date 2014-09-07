/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TimeZone;

/**
 *
 * @author mat
 */
public class CommitChecker extends Thread {

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits) {
        this.gitHubCommits = gitHubCommits;
        caller = new GitHubCaller();
    }

    @Override
    public void run() {
        // TODO refactor date into its own class
        while (true) {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(tz);

            String since = null;
            if (!gitHubCommits.isEmpty()) {
                Date lastDate = gitHubCommits.lastKey();

                Calendar cal = Calendar.getInstance();
                cal.setTime(lastDate);

                // Add one second otherwise github returns the commit that happened on that date
                cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 1);

                lastDate = cal.getTime();
                since = df.format(lastDate);
            }
            Calendar calender = Calendar.getInstance();
            calender.set(Calendar.DATE, calender.getActualMaximum(Calendar.DATE));

            String until = df.format(calender.getTime());

            Map<Date, GitHubCommit> data = caller.getCommits(since, until);

            gitHubCommits.putAll(data);

            try {
                sleep(60000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }

}
