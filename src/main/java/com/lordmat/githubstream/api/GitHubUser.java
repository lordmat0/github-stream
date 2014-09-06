/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

/**
 *
 * @author mat
 */
//TODO Finish this class off including adding XML information
public class GitHubUser {

    private String userName;
    private String accountUrl;
    private String avatarUrl;

    public GitHubUser(String userName, String accountUrl, String avatarUrl) {
        this.userName = userName;
        this.accountUrl = accountUrl;
        this.avatarUrl = avatarUrl;
    }

    
    // TODO define an hashcode/equals method 
}
