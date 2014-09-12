/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

import com.lordmat.githubstream.resource.MyResourceBundle;
import com.lordmat.githubstream.resource.ResourceKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mat
 */
public class Path {

    private final static Logger LOGGER = Logger.getLogger(Path.class.getName());
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
     * Repository name used for finding information about commits
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
        String token = MyResourceBundle.getString(ResourceKey.AUTH_TOKEN);

        DEFAULT_PATH = "https://api.github.com/";
        String paths = null;
        try {
            paths = ClientBuilder.newClient().target(DEFAULT_PATH)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", " token " + token)
                    .get(String.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Could not get path list", ex);
            throw new RuntimeException("Error occured trying to"
                    + " get path list: " + ex.getMessage());
        }

        try {
            JSONObject jsonPaths = new JSONObject(paths);

            RATE_LIMIT = jsonPaths.getString("rate_limit_url");

            REPO_NAME = MyResourceBundle.getString(ResourceKey.REPO_NAME);
            REPO_OWNER = MyResourceBundle.getString(ResourceKey.REPO_OWNER);

            REPO_COMMITS = jsonPaths.getString("repository_url")
                    .replace("{owner}", REPO_OWNER)
                    .replace("{repo}", REPO_NAME) + "/commits";

            USER_URL = jsonPaths.getString("user_url");
        } catch (JSONException jEx) {
            LOGGER.log(Level.SEVERE, "Error with jsonformat "
                    + "from default paths:\n " + paths, jEx);
            
            throw new RuntimeException("Error with jsonformat "
                    + "from default paths:\n " + paths + "\n" + jEx.getMessage());
        }
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
