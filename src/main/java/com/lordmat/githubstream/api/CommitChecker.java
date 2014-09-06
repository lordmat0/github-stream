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

    private GitHubCaller caller;
    private NavigableMap<Date, GitHubCommit> gitHubCommits;

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits) {
        this.gitHubCommits = gitHubCommits;
        caller = new GitHubCaller();
    }

    @Override
    public void run() {
        
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        
        String since = null;
        Date lastDate = null;
        if (gitHubCommits.isEmpty()) {
            //TODO remove this as if since is null it will still return data
            df.setTimeZone(tz);

            Calendar calender = Calendar.getInstance();
            calender.set(Calendar.DATE, calender.getActualMinimum(Calendar.DATE));

            df.setCalendar(calender);

            lastDate = calender.getTime();
        } else {
            lastDate = gitHubCommits.lastKey();
            since = df.format(lastDate);
        }
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DATE, calender.getActualMaximum(Calendar.DATE));

        String until = df.format(calender.getTime());

        Map<Date, GitHubCommit> data = caller.getCommits(since, until);

        for (Map.Entry<Date, GitHubCommit> entry : data.entrySet()) {
            //System.out.println(entry.getValue());
        }
        
        // TODO check if this is put int the right order
        gitHubCommits.putAll(data);
    }

}
