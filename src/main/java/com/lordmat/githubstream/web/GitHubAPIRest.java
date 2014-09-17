package com.lordmat.githubstream.web;

import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.bean.UserList;
import com.lordmat.githubstream.data.GitHubData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 * <p>
 * Web clients can all these methods to get the lastest commits, past commits
 * and to get user details
 *
 * @author mat
 */
@Path("githubapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubAPIRest {

    private final static Logger LOGGER = Logger.getLogger(GitHubAPIRest.class.getName());

    @Context
    private UriInfo context;

    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final Map<String, GitHubUser> gitHubUsers;
    private final GitHubData gitHubData;

    /**
     * Created every time the webAPI is hit
     */
    public GitHubAPIRest() {
        gitHubData = StartManager.data();
        gitHubCommits = StartManager.data().getCommits();
        gitHubUsers = StartManager.data().getUsers();
    }

    /**
     * Returns a list of github users details from the list passed in. If the
     * user is not found then it is not put in the list. The list returned will
     * never be null or contain null elements but is possible to be empty if no
     * users are found from the list given.
     * <p>
     *
     * <code>
     * $.ajax('res/githubapi/user/', {
     * <br>
     * contentType: 'application/json',
     * <br>
     * type: 'POST',
     * <br>
     * data: JSON.stringify({"users":["user1","user2"]})
     * <br>
     * });
     * </code>
     *
     * @param users List of users to find
     * @return a list containing found users details
     */
    @Path("user")
    @POST
    public List<GitHubUser> getUsers(UserList users) {

        List<GitHubUser> returnedUsers = new ArrayList<>();
        for (String userName : users.getUsers()) {

            GitHubUser user = gitHubUsers.get(userName);

            // User returns null if it doesn't exist and not used in any commits
            if (user == null) {
                continue;
            }

            returnedUsers.add(user);
        }

        return returnedUsers;
    }

    /**
     * Checks for new commits, if the date passed in null or empty then a empty
     * list is returned
     *
     * @param latestCommitDate The commit ID to check against
     * @return An empty list or commits that come after the lastestCommitId
     *
     */
    @Path("commit/new")
    @POST
    public List<GitHubCommit> getNewCommits(String latestCommitDate) {
        return gitHubData.getNewCommits(latestCommitDate);
    }

    /**
     *
     * @param earlistCommitDate The commit ID to check against
     * @return An empty list or commits that come before the earlistCommitId
     */
    @Path("commit/old")
    @POST
    public List<GitHubCommit> getOldCommits(String earlistCommitDate) {
        return gitHubData.getOldCommits(earlistCommitDate);
    }

}
