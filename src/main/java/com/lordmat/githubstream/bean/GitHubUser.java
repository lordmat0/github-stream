package com.lordmat.githubstream.bean;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains details of a GitHubUser
 *
 * @author mat
 */
@XmlRootElement
public class GitHubUser {

    /**
     * Github username
     */
    @XmlElement
    private String userName;

    /**
     * GitHub url to view there account on github
     */
    @XmlElement
    private String accountUrl;

    /**
     * Avatar URL, this is the url of there avatar (picture)
     */
    @XmlElement
    private String avatarUrl;

    /**
     * Needed for Java bean
     */
    public GitHubUser() {
    }

    /**
     *
     * @param userName GitHub Username
     * @param accountUrl GitHub url to view there account on github
     * @param avatarUrl Avatar URL, this is the url of there avatar (picture)
     */
    public GitHubUser(String userName, String accountUrl, String avatarUrl) {
        this.userName = userName;
        this.accountUrl = accountUrl;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.userName);
        hash = 41 * hash + Objects.hashCode(this.accountUrl);
        hash = 41 * hash + Objects.hashCode(this.avatarUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GitHubUser other = (GitHubUser) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.accountUrl, other.accountUrl)) {
            return false;
        }
        return Objects.equals(this.avatarUrl, other.avatarUrl);
    }

    @Override
    public String toString() {
        return "GitHubUser{" + "userName=" + userName
                + ", accountUrl=" + accountUrl
                + ", avatarUrl=" + avatarUrl + '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
