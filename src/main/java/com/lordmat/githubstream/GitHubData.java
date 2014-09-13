package com.lordmat.githubstream;

import com.lordmat.githubstream.api.CommitChecker;
import com.lordmat.githubstream.api.GitHubCaller;
import com.lordmat.githubstream.api.GitHubCommit;
import com.lordmat.githubstream.api.GitHubUser;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * This class provides access to github users stored and commits.
 *
 * @author mat
 */
public class GitHubData {

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
     * Returns a unmodifiable a thread safe, sorted by keys map containing
     * commits
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

    public GitHubCaller getCaller() {
        return caller;
    }

    // TODO maybe GitHubData should handle adding data to commits and users, just so this class can validate
}
