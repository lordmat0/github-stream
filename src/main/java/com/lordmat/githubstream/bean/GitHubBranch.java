/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.bean;

import java.util.Date;
import java.util.TreeSet;

/**
 * Contains details for a GitHub branch
 *
 * @author mat
 */
public class GitHubBranch {

    /**
     * Name of the branch
     */
    private String name;

    /**
     * The Secure Hash Algorithm of the branch, needed for getting the commits
     * of the branch
     */
    private String sha;
    
    /**
     * Holds a reference to the commits this branch has done
     */
    private TreeSet<Date> commits;

    private boolean hasLastCommit;
    
    /**
     * Needed for javabean
     */
    public GitHubBranch(){
    }

    public GitHubBranch(String name, String sha) {
        this.name = name;
        this.sha = sha;
        this.commits = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public void setCommits(TreeSet<Date> commits) {
        this.commits = commits;
    }
    
    public TreeSet<Date> getCommits() {
        return commits;
    }
    
    public void setHasLastCommit(boolean hasLastCommit){
        this.hasLastCommit = hasLastCommit;
    }
    
    public boolean getHasLastCommit(){
        return hasLastCommit;
    }
    
}
