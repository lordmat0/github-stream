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

/**
 * This class extends AbstractChecker to implement the query method. This object
 * will check the GitHubApi every 30 seconds to get the latest commits. The map
 * passed in is thread-safe
 *
 * @author mat
 */
public class CommitChecker extends AbstractChecker {

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final GitHubBranch branch;

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits, GitHubBranch branch) {
        this(gitHubCommits, branch, 60000);
    }

    public CommitChecker(NavigableMap<Date, GitHubCommit> gitHubCommits, GitHubBranch branch, int queryTime) {
        super(queryTime);
        this.gitHubCommits = gitHubCommits;
        caller = new GitHubCaller();
        this.branch = branch;
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

            Map<Date, GitHubCommit> data = caller.getCommits(since, null, branch.getSha());

            branch.getCommits().addAll(data.keySet());

            gitHubCommits.putAll(data);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "CommitChecker threw an error, re-trying", ex);
        }
    }

}
