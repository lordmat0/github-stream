/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

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

    private Map<String, GitHubUser> gitHubUsers;
    private NavigableMap<Date, GitHubCommit> gitHubCommits;
    
    private Date latestCommitDate;
    private Date latestCheckDate;
    
    private Date earlistCommitDate;
    private Date earlistCheckDate;
    private GitHubCaller call = new GitHubCaller();
    

    public GitHubAPI() {
        gitHubUsers = new ConcurrentHashMap<>();
        gitHubCommits = new ConcurrentSkipListMap<>();
        
        new CommitChecker(gitHubCommits).start();
    }

    // TODO change this to an iterator? maybe impossible due to concurrencly
    public NavigableMap<Date, GitHubCommit> getCommits(){
        return gitHubCommits;
    }
    
    public List<GitHubCommit> checkForNewCommits(){
        Date now = new Date();
        // Add 60 seconds on current date
        
        // check to see if it's passed the lastestCheckDate
        
        // If true
            // Do a call to github API with the latestCommited date as the from
            
            //New commits?
                // if true add them to the list
                // Get latest time and change lastestCommmitDate
                // return only new commits
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<GitHubCommit> findOldCommits(Date date){
        // Maybe get total number of commits?
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public List<GitHubUser> findUser(List<String> gitHubUsers){
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
