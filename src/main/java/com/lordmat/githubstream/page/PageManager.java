/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.page;

import com.lordmat.githubstream.api.GitHubCommit;
import com.lordmat.githubstream.web.StartManager;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * This class will handle loading the page on refresh, it caches the commits in
 * a StringBuilder and only adds new ones to the list. This class will also
 * handle adding html tags and classes.
 *
 * @author mat
 */
public class PageManager {

    private final static StringBuilder page = new StringBuilder();

    private final static NavigableMap<Date, GitHubCommit> commitList = StartManager.gitHubAPI.getCommits();
    private static Date lastDate;

    public static synchronized String makePage() {

        NavigableMap<Date, GitHubCommit> commitCapture = new TreeMap<>(commitList);
        // First load
        if (lastDate == null) {
            for (NavigableMap.Entry<Date, GitHubCommit> entry : commitCapture.descendingMap().entrySet()) {
                page.append("<p>");
                page.append(entry.getValue());
                page.append("</p>");
            }
            // Check if still waiting for data
            if (!commitCapture.isEmpty()) {
                lastDate = commitCapture.lastKey();
            }
        } else if (!lastDate.equals(commitCapture.lastEntry().getKey())) {

            // Need to put it into a new string builder otherwise the order
            // would be wrong
            StringBuilder newCommits = new StringBuilder();

            for (NavigableMap.Entry<Date, GitHubCommit> entry : commitCapture.descendingMap().entrySet()) {
                if (entry.getKey().equals(lastDate)) {
                    break;
                }

                // todo sanitize getValue(), change tags add html classes
                newCommits.append("<p>");
                newCommits.append(entry.getValue());
                newCommits.append("</p>");

            }

            page.insert(0, newCommits);

            lastDate = commitCapture.lastKey();
        }

        return page.toString();
    }

}
