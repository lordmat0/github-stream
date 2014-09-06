package com.lordmat.githubstream.web;

import com.lordmat.githubstream.api.GitHubUser;
import com.lordmat.githubstream.api.GitHubCommit;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author mat
 */
@Path("githubapi")
@Produces("application/json")
@Consumes("application/json")
public class GitHubAPIRest {

    @Context
    private UriInfo context;

    /**
     * Created every time the webAPI is hit
     */
    public GitHubAPIRest() {
        System.out.println("GitHubAPI created");
    }

    /**
     * 
     * @param users List of users to find information about
     * @return A list of github users details requested
     */
    @Path("user/")
    @GET
    public List<GitHubUser> getUsers(String[] users) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param lasestCommitId The commit ID to check against 
     * @return An empty list or commits that come after the lastestCommitId
     * 
     */
    @Path("commit/new")
    @GET
    public List<GitHubCommit> getNewCommits(String lasestCommitId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param earlistCommitId The commit ID to check against 
     * @return An empty list or commits that come before the earlistCommitId
     */
    @Path("commit/old")
    @GET
    public List<GitHubCommit> getOldCommits(String earlistCommitId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
