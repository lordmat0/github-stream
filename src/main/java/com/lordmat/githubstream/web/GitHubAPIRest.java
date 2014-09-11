package com.lordmat.githubstream.web;

import com.lordmat.githubstream.api.GitHubAPI;
import com.lordmat.githubstream.api.GitHubUser;
import com.lordmat.githubstream.api.GitHubCommit;
import com.lordmat.githubstream.bean.UserList;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
//TODO finish this class
@Path("githubapi")
@Produces("application/json")
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubAPIRest {

    @Context
    private UriInfo context;
    private final GitHubAPI gitHubAPI;

    /**
     * Created every time the webAPI is hit
     */
    public GitHubAPIRest() {
        System.out.println("GitHubAPI created");
        gitHubAPI = new GitHubAPI();
    }

    /**
     * Returns a details of users requested, example jQuery code below.
     *
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
     * @param users List of users to find information about
     * @return A list of github users details requested
     */
    @Path("user/")
    @POST
    public List<GitHubUser> getUsers(UserList users) {
        return gitHubAPI.findUser(users.getUsers());
    }

    /**
     *
     * @param latestDate The commit ID to check against
     * @return An empty list or commits that come after the lastestCommitId
     *
     */
    @Path("commit/new")
    @POST
    public List<GitHubCommit> getNewCommits(String latestCommitDate) {
        return gitHubAPI.checkForNewCommits(latestCommitDate);
    }

    /**
     *
     * @param earlistCommitId The commit ID to check against
     * @return An empty list or commits that come before the earlistCommitId
     */
    @Path("commit/old")
    @POST
    public List<GitHubCommit> getOldCommits(String earlistCommitId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
