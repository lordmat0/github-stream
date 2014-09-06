/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import java.util.HashMap;
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
     * @param since The start date
     * @param until The end date
     * @return a JSONArray that contains details on commits which are retrieved
     * from an API call to the githubAPI
     */
    public JSONArray getCommits(String since, String until) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("since", since);
        queryParam.put("until", until);

        return new JSONArray(call(Path.REPO_COMMITS, queryParam));
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
            
            for(Map.Entry<String, String> entry : parameter.entrySet()){
                webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", " token " + token)
                .get(String.class);

    }

}
