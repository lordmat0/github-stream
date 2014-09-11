package com.lordmat.githubstream.web;

import com.lordmat.githubstream.api.GitHubAPI;
import com.lordmat.githubstream.api.GitHubUser;
import com.lordmat.githubstream.api.GitHubCommit;
import java.util.List;
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
//TODO finish this class
@Path("githubapi")
@Produces("application/json")
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubAPIRest {

    @Context
    private UriInfo context;
    
    private GitHubAPI gitHubApi;

    /**
     * Created every time the webAPI is hit
     */
    public GitHubAPIRest() {
        System.out.println("GitHubAPI created");
        gitHubApi = new GitHubAPI();
    }

    /**
     *
     * @param users List of users to find information about
     * @return A list of github users details requested
     */
    @Path("user/")
    @POST
    public List<GitHubUser> getUsers(List<String> users) {
        return gitHubApi.findUser(users);
    }

    /**
     *
     * @param lasestCommitId The commit ID to check against
     * @return An empty list or commits that come after the lastestCommitId
     *
     */
    @Path("commit/new")
    @POST
    public List<GitHubCommit> getNewCommits(String lastCommitDate) {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @Path("test1/")
    @POST
    public GitHubUser test1(GitHubUser user) {
        if (user != null) {
            System.out.println(user);
            return user;
        }

        return new GitHubUser("Nope", "Didn't work", " try again");
    }

    /*
    this works but GET doesn't
    
     $.ajax('', {
     contentType: 'application/json',
     type: 'POST',
     data: JSON.stringify({
     'userName': 'No2pe',
     'accountUrl': 'Didn\'t work',
     'avatarUrl': ' try again'
     }
     )
     });

     */

}
