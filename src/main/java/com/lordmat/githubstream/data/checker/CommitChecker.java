package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.data.GitHubCaller;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO change this to return a result of gitHubCommits
/**
 * This class checks its branch for new commits and updates it's branch list of
 * dates and adds the commit details in gitHubCommits
 *
 * @author mat
 */
public class CommitChecker extends Thread {

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final GitHubBranch branch;

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits, GitHubBranch branch) {
        this(gitHubCommits, branch, 60000);
    }

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits, GitHubBranch branch, int queryTime) {
        this.gitHubCommits = gitHubCommits;
        caller = new GitHubCaller();
        this.branch = branch;
    }

    @Override
    public void run() {
        query();
    }

    /**
     * Handles querying GitHub
     */
    protected void query() {
        try {
            String since = null;

            // If it's not empty then we've already have some commits 
            // have some commits , so use the last date to search on
            if (!branch.getCommits().isEmpty()) {

                Calendar cal = Calendar.getInstance();
                cal.setTime(branch.getCommits().last());

                // Add one second otherwise github returns the commit that happened on that date
                cal.add(Calendar.SECOND, 1);

                since = DateTimeFormat.format(cal.getTime());
            }

            Map<Date, GitHubCommit> data = caller.getCommits(since, null, branch.getSha());

            branch.getCommits().addAll(data.keySet());

            gitHubCommits.putAll(data);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CommitChecker threw an error, re-trying", ex);
        }
    }

}
