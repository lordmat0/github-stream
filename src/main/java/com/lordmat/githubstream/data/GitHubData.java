package com.lordmat.githubstream.data;

import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;

/**
 * This class provides access to github users stored and commits.
 *
 * @author mat
 */
public class GitHubData {

    private final static Logger LOGGER = Logger.getLogger(GitHubData.class.getName());

    private final Map<String, GitHubUser> gitHubUsers;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final GitHubCaller caller;

    public GitHubData() {
        gitHubUsers = new ConcurrentHashMap<>();
        gitHubCommits = new ConcurrentSkipListMap<>();
        caller = new GitHubCaller();

        new CommitChecker(gitHubCommits).start();
    }

    /**
     * Returns an unmodifiable thread safe NavigableMap, sorted by keys map
     * containing commits.
     *
     * @return List of commits
     */
    public NavigableMap<Date, GitHubCommit> getCommits() {
        return Collections.unmodifiableNavigableMap(gitHubCommits);
    }

    /**
     * Returns a thread safe map containing users
     *
     * @return
     */
    public Map<String, GitHubUser> getUsers() {
        return gitHubUsers;
    }

    /**
     * Checks for new commits, if the date passed in null or empty then a empty
     * list is returned
     *
     * @param latestCommitDate The commit ID to check against
     * @return An empty list or commits that come after the lastestCommitId
     *
     */
    public List<GitHubCommit> getNewCommits(String latestCommitDate) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (latestCommitDate == null || latestCommitDate.isEmpty()) {
            return newCommits;
        }

        Date date = DateTimeFormat.parse(latestCommitDate);

        if (gitHubCommits.isEmpty()) {
            LOGGER.info("Githubcommit list is empty");
            return newCommits;
        }

        if (gitHubCommits.lastKey().equals(date)) {
            return newCommits; // no new commits
        }

        // get a subset of the list
        newCommits.addAll(gitHubCommits.tailMap(date).values());
        Collections.reverse(newCommits);

        return newCommits;
    }

    /**
     * I believe this needs to be synchronized because otherwise two requests
     * would mean that it could be calling getCommits twice on the same date
     * wasting bandwidth
     *
     * @param earlistCommitDate
     * @return
     */
    public synchronized List<GitHubCommit> getOldCommits(String earlistCommitDate) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (earlistCommitDate == null || earlistCommitDate.isEmpty()) {
            return newCommits;
        }

        Date date = DateTimeFormat.parse(earlistCommitDate);

        if (!gitHubCommits.containsKey(date)) {
            
            date = gitHubCommits.firstKey();
            
            // If we don't contain the date assume we need to get more commits,
            // but don't trust the client date passed in
            Map<Date, GitHubCommit> mapCommits = caller.getCommits(null, 
                    DateTimeFormat.format(date));

            gitHubCommits.putAll(mapCommits);
        }

        // get a subset of the list
        newCommits.addAll(gitHubCommits.headMap(date).values());
        Collections.reverse(newCommits);

       
         // Limit size to 30
        int newCommitsSize = newCommits.size();
        return newCommits.subList(0, (newCommitsSize >= 30 ? 30 : newCommitsSize));
    }

}
