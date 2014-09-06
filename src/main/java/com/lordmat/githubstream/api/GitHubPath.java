/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author mat
 */
public class GitHubPath {

    public static final String DEFAULT_PATH;
    public static final String RATE_LIMIT;
    public static final String REPO_NAME;
    public static final String REPO_OWNER;
    public static final String COMMITS;


    private static final String USER_URL;

    static {
        DEFAULT_PATH = "https://api.github.com/";

        String paths = ClientBuilder.newClient().target(DEFAULT_PATH)
                //.queryParam("foo", "bar")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        JSONObject jsonPaths = new JSONObject(paths);

        RATE_LIMIT = jsonPaths.getString("rate_limit_url");

        // TODO read this from a file
        REPO_NAME = "githubstream";
        REPO_OWNER = "lordmat0";

        COMMITS = jsonPaths.getString("repository_url")
                .replace("{owner}", REPO_OWNER)
                .replace("{repo}", REPO_NAME) + "/commits";

        USER_URL = jsonPaths.getString("user_url");
    }

    /**
     * 
     * @param username the user name to find
     * @return A path to the user to be called on
     */
    public static String user(String username){
        return USER_URL.replace("{user}", username);
    }
    
    /**
     * Can't make instance of class
     */
    private GitHubPath() {
    }
}
