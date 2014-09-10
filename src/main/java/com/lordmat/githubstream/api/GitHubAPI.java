/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 * @author mat
 */
//TODO Finish this class
public class GitHubAPI {

    private final Map<String, GitHubUser> gitHubUsers;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;

    private final GitHubCaller call = new GitHubCaller();

    public GitHubAPI() {
        gitHubUsers = new ConcurrentHashMap<>();
        gitHubCommits = new ConcurrentSkipListMap<>();

        new CommitChecker(gitHubCommits).start();
    }

    // TODO change this to an iterator? maybe impossible due to concurrencly
    public NavigableMap<Date, GitHubCommit> getCommits() {
        return Collections.unmodifiableNavigableMap(gitHubCommits);
    }

    public List<GitHubCommit> checkForNewCommits(Date date) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (gitHubCommits.lastKey().equals(date)) {
            return newCommits; // no new commits
        }

        for (GitHubCommit commit : gitHubCommits.values()) {
            if (commit.getDate().equals(date)) {
                break;
            }
            newCommits.add(commit);
        }

        return newCommits;
    }

    public List<GitHubCommit> findOldCommits(Date date) {
        // Maybe get total number of commits?

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<GitHubUser> findUser(List<String> gitHubUserFind) {
        List<GitHubUser> returnedUsers = new ArrayList<>(gitHubUserFind.size());
        for (String userName : gitHubUserFind) {
            
            GitHubUser user = gitHubUsers.get(userName);
            
            if(user == null){
                user = call.getUser(userName);
                // Cache user
                gitHubUsers.put(user.getUserName(), user);
            }

            returnedUsers.add(user);
        }

        return returnedUsers;
    }
}
