package com.lordmat.githubstream.api;

import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.logging.Logger;

/**
 *
 * @author mat
 */
//TODO Finish this class
public class GitHubAPI {

    private final static Logger LOGGER = Logger.getLogger(GitHubAPI.class.getName());

    NavigableMap<Date, GitHubCommit> gitHubCommits;
    Map<String, GitHubUser> gitHubUsers;

    public GitHubAPI() {
        gitHubCommits = StartManager.getData().getCommits();
        gitHubUsers = StartManager.getData().getUsers();
    }

    /**
     * Checks for new commits, if the date passed in null or empty then a empty
     * list is returned
     *
     * @param date
     * @return
     */
    public List<GitHubCommit> checkForNewCommits(String date) {
        if (date == null || date.isEmpty()) {
            return new ArrayList<>();
        }

        return checkForNewCommits(DateTimeFormat.parse(date));
    }

    /**
     * Untested
     *
     * @param date
     * @return
     */
    public List<GitHubCommit> checkForNewCommits(Date date) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (gitHubCommits.isEmpty()) {

            LOGGER.info("Githubcommit list is empty");
            return newCommits;
        }

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

    /**
     * Returns a list of github users details from the list passed in. If the
     * user is not found then it is not put in the list. The list returned will
     * never be null or contain null elements but is possible to be empty if no
     * users are found from the list given.
     *
     * @param gitHubUserFind List of users to find
     * @return a list containing found users details
     */
    public List<GitHubUser> findUser(List<String> gitHubUserFind) {
        List<GitHubUser> returnedUsers = new ArrayList<>();
        for (String userName : gitHubUserFind) {

            GitHubUser user = gitHubUsers.get(userName);

            // Not cached
            if (user == null) {
                user = StartManager.getData().getCaller().getUser(userName);

                // User returns null if doesn't exist
                if (user == null) {
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
