/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lordmat.githubstream.api;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author mat
 */
//TODO write XML mapping to return JSON
public class GitHubCommit {
    private String id;
    private Date date;
    private String message;
    private List<String> filesChanged;
    
    private String userCommited;

    public GitHubCommit(String id, Date date, String message, List<String> filesChanged, String userCommited) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.filesChanged = filesChanged;
        this.userCommited = userCommited;
    }
    
    public Date getDate(){
        return date;
    }

    @Override
    public String toString() {
        return id + ", " + date.toString() + ", " + message + ", " + userCommited;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getFilesChanged() {
        return filesChanged;
    }

    public void setFilesChanged(List<String> filesChanged) {
        this.filesChanged = filesChanged;
    }

    public String getUserCommited() {
        return userCommited;
    }

    public void setUserCommited(String userCommited) {
        this.userCommited = userCommited;
    }
    
    
    
}
