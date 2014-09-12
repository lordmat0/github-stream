/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

/**
 *
 * @author mat
 */
//TODO Finish this class
public class GitHubAPI {

    NavigableMap<Date, GitHubCommit> gitHubCommits;
    Map<String, GitHubUser> gitHubUsers;

    public GitHubAPI() {
        gitHubCommits = StartManager.getData().getCommits();
        gitHubUsers = StartManager.getData().getUsers();
    }

    public List<GitHubCommit> checkForNewCommits(String date){
        return checkForNewCommits(DateTimeFormat.parse(date));
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
        List<GitHubUser> returnedUsers = new ArrayList<>();
        for (String userName : gitHubUserFind) {
            
            GitHubUser user = gitHubUsers.get(userName);
            
            // Not cached
            if(user == null){
                user = StartManager.getData().getCaller().getUser(userName);
                
                // User returns null if doesn't exist
                if(user == null){
                    continue;
                }
                
                // Cache user
                gitHubUsers.put(user.getUserName(), user);
            }

            returnedUsers.add(user);
        }

        return returnedUsers;
    }
}
