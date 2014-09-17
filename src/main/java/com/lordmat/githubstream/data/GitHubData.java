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

    public synchronized List<GitHubCommit> getOldCommits(String earlistCommitDate) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (earlistCommitDate == null || earlistCommitDate.isEmpty()) {
            return newCommits;
        }

       Date date = DateTimeFormat.parse(earlistCommitDate);
        
        

        if (!gitHubCommits.containsKey(date)) {
            Map<Date, GitHubCommit> mapCommits = caller.getCommits(null, DateTimeFormat.format(date));

            gitHubCommits.putAll(mapCommits);
        }

        // get a subset of the list
        newCommits.addAll(gitHubCommits.headMap(date).values());
        Collections.reverse(newCommits);

        return newCommits;
    }

    // TODO maybe GitHubData should handle adding data to commits and users, just so this class can validate
}
