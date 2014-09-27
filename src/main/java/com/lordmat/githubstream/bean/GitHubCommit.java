package com.lordmat.githubstream.bean;

import com.lordmat.githubstream.resource.Path;
import java.util.Date;
import java.util.List;

/**
 * This class contains details of a commit
 *
 * @author mat
 */
public class GitHubCommit {

    private String id;
    private String idUrl;
    private Date date;
    private String message;
    private List<String> filesChanged;

    private GitHubUser userCommited;

    public GitHubCommit() {

    }

    public GitHubCommit(String id, Date date, String message, List<String> filesChanged, GitHubUser userCommited) {
        this.id = id;
        this.date = date;
        this.message = message;
        this.filesChanged = filesChanged;
        this.userCommited = userCommited;

        idUrl = Path.COMMIT_ID_PATH + id;
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

    public String getIdUrl() {
        return idUrl;
    }

    public void setFilesChanged(List<String> filesChanged) {
        this.filesChanged = filesChanged;
    }

    public GitHubUser getUserCommited() {
        return userCommited;
    }

    public void setUserCommited(GitHubUser userCommited) {
        this.userCommited = userCommited;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIdUrl(String idUrl) {
        this.idUrl = idUrl;
    }

    /**
     *
     * @return The date of the commit
     */
    public Date getDate() {
        return date;
    }

}
