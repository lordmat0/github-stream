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
public class Path {

    /**
     * Default path of github API
     */
    public static final String DEFAULT_PATH;

    /**
     * Path of rate limit which returns data about how many calls can be
     * performed and when it's reset
     */
    public static final String RATE_LIMIT;

    /**
     * Repoistory name used for finding information about commits
     */
    public static final String REPO_NAME;

    /**
     * Repository owner needed for finding information about commits
     */
    public static final String REPO_OWNER;

    /**
     * Path needed for querying commits information
     */
    public static final String REPO_COMMITS;

    /**
     * Path needed to get user information, use method user() instead
     */
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

        REPO_COMMITS = jsonPaths.getString("repository_url")
                .replace("{owner}", REPO_OWNER)
                .replace("{repo}", REPO_NAME) + "/commits";

        USER_URL = jsonPaths.getString("user_url");
    }

    /**
     *
     * @param username the user name to find
     * @return A path to the user to be called on
     */
    public static String user(String username) {
        return USER_URL.replace("{user}", username);
    }

    /**
     * Private constructor, can't make instance of class
     */
    private Path() {
    }
}
