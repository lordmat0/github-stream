package com.lordmat.githubstream.resource;

import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.Calendar;
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
     * Path for commit ID's
     */
    public static final String COMMIT_ID_PATH;
    
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

            // Check Rate Limit
            String result = ClientBuilder.newClient().target("https://api.github.com/rate_limit")
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", " token " + token)
                    .get(String.class);

            JSONObject jsonRateLimit = new JSONObject(result).getJSONObject("rate");

            if (jsonRateLimit.getInt("remaining") == 0) {
                Calendar unixtimestamp = Calendar.getInstance();
                unixtimestamp.setTimeInMillis((long) jsonRateLimit.getInt("reset") * 1000);

                LOGGER.severe("Rate_limit is zero, can't make any github requests, wait till: " 
                        + DateTimeFormat.getTime(unixtimestamp.getTime()));

                System.exit(1);
            }

            LOGGER.log(Level.SEVERE, "Could not get path list path=" + paths, ex);
            throw new RuntimeException("Error occured trying to"
                    + " get path list path=" + paths + "\n" + ex.getMessage());
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
            
            COMMIT_ID_PATH = "https://github.com/" + REPO_OWNER + "/" + REPO_NAME + "/commit/";
            
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
