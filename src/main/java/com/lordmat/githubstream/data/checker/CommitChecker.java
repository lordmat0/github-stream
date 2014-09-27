package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.data.GitHubCaller;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the fixed interval to check the public github api. The map
 * it updates is thread safe.
 *
 * @author mat
 */
public class CommitChecker extends AbstractChecker {

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits) {
        super(30000);
        this.gitHubCommits = gitHubCommits;
        caller = new GitHubCaller();
    }

    /**
     * Handles querying GitHub
     */
    @Override
    protected void query() {
        try {
            String since = null;

            // If it's not empty then we've already have some commits 
            // have some commits , so use the last date to search on
            if (!gitHubCommits.isEmpty()) {

                Calendar cal = Calendar.getInstance();
                cal.setTime(gitHubCommits.lastKey());

                // Add one second otherwise github returns the commit that happened on that date
                cal.add(Calendar.SECOND, 1);

                since = DateTimeFormat.format(cal.getTime());
            }

           Map<Date, GitHubCommit> data = caller.getCommits(since, null);

            gitHubCommits.putAll(data);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CommitChecker threw an error, re-trying", ex);
        }
    }

}
