/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.bean;

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
     * Needed for javabean
     */
    public GitHubBranch(){
    }

    public GitHubBranch(String name, String sha) {
        this.name = name;
        this.sha = sha;
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

}
