package com.lordmat.githubstream.bean;

import java.util.Date;
import java.util.List;

/**
 * This class contains details of a commit
 *
 * @author mat
 */
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

    /**
     *
     * @return The date of the commit
     */
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return id + ", " + date.toString() + ", " + message + ", " + userCommited;
    }

    /**
     *
     * @return The id of the commit
     */
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
