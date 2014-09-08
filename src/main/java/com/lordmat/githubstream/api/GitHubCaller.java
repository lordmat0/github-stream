/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import com.lordmat.githubstream.util.GitDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mat
 */
public class GitHubCaller {

    private final String token;

    /**
     * Default constructor, attempts to load from resource bundle "project"
     */
    public GitHubCaller() {
        ResourceBundle bundle = ResourceBundle.getBundle("project");
        token = bundle.getString("authtoken");
    }

    /**
     * Test method will be deleted
     *
     * @return
     */
    public JSONObject getPaths() {
        return new JSONObject(call(Path.DEFAULT_PATH));
    }

    /**
     * This includes details on how many responses are allowed to make and when
     * that limit is reset
     *
     * @return a JSONObject from results from an API call to the githubAPI
     */
    public JSONObject getRateLimit() {
        return new JSONObject(call(Path.RATE_LIMIT));
    }

    /**
     * Gets a list of commits between two dates.
     *
     * @param since The start date, can be null (meaning that there is no
     * restriction)
     * @param until The end date, can be null (meaning that there is no
     * restriction)
     * @return a JSONArray that contains details on commits which are retrieved
     * from an API call to the githubAPI
     */
    public Map<Date, GitHubCommit> getCommits(String since, String until) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("since", since);
        queryParam.put("until", until);

        JSONArray commits = new JSONArray(call(Path.REPO_COMMITS, queryParam));

        Map<Date, GitHubCommit> gitHubCommits = new LinkedHashMap<>();

        for (int i = commits.length() - 1; i >= 0; i--) {

            JSONObject commitDetails = commits.getJSONObject(i);
            JSONObject commit = commitDetails.getJSONObject("commit");
            JSONObject author = commit.getJSONObject("author");

            String id = commitDetails.getString("sha");
            String message = commit.getString("message");
            String user = author.getString("name");

            Date date = null;

            date = GitDateFormat.parse(author.getString("date"));

            //TODO work out a easy way to get files or remove it
            GitHubCommit ghCommit = new GitHubCommit(id, date, message, null, user);

            gitHubCommits.put(date, ghCommit);
        }

        //Uncomment for fake commits
        /*
        gitHubCommits.put(
                GitDateFormat.parse(GitDateFormat.format(new Date())),
                new GitHubCommit("fake", new Date(), "fake", null, "fake"));
        */
        
        return gitHubCommits;
    }

    /**
     * Get details of a github user
     *
     * @param userName
     * @return a GitHubUser thats details are retrieved from an API call to the
     * githubAPI.
     */
    public GitHubUser getUser(String userName) {
        JSONObject user = new JSONObject(call(Path.user(userName)));
        return new GitHubUser(
                user.getString("login"),
                user.getString("url"),
                user.getString("avatar_url")
        );
    }

    /**
     * Makes a call to the githubAPI with variables passed in.
     *
     * @param path The URL to call
     * @return Results from the call
     */
    private String call(String path) {
        return call(path, null);
    }

    /**
     * Makes a call to the githubAPI with variables passed in.
     *
     * @param path The URL to call
     * @param parameter Extra parameters used
     * @return Results from the call
     */
    private String call(String path, Map<String, String> parameter) {
        WebTarget webTarget = ClientBuilder.newClient().target(path);

        if (parameter != null) {

            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", " token " + token)
                .get(String.class);

    }

}
