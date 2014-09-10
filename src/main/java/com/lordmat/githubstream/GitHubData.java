/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    
    public NavigableMap<Date, GitHubCommit> getCommits() {
        return Collections.unmodifiableNavigableMap(gitHubCommits);
    }
    
    public Map<String, GitHubUser> getUsers(){
        return gitHubUsers;
    }
    
    public GitHubCaller getCaller(){
        return caller;
    }
}
