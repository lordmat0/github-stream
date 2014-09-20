package com.lordmat.githubstream.data;

import com.lordmat.githubstream.resource.Path;
import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.resource.MyResourceBundle;
import com.lordmat.githubstream.resource.ResourceKey;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class handles provides an interface to the public githubapi
 *
 * @author mat
 */
public class GitHubCaller {

    private final static Logger LOGGER = Logger.getLogger(GitHubCaller.class.getName());

    private final String token;

    /**
     * Default constructor, attempts to load from resource bundle "project"
     */
    public GitHubCaller() {
        token = MyResourceBundle.getString(ResourceKey.AUTH_TOKEN);
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
    public NavigableMap<Date, GitHubCommit> getCommits(String since, String until) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("since", since);
        queryParam.put("until", until);

        String data = call(Path.REPO_COMMITS, queryParam);
        
        JSONArray commits = new JSONArray(data);

        NavigableMap<Date, GitHubCommit> gitHubCommits = new TreeMap<>();

        // Check results
        if (commits.length() == 0) {
            LOGGER.fine("No commits retrieved");
        } else if (!commits.getJSONObject(0).has("commit")) {
            LOGGER.warning("getCommits did not receive a"
                    + " list of commits (maybe a 404?)");
        }

        Map<String, GitHubUser> ghUsers = StartManager.data().getUsers();
        for (int i = commits.length() - 1; i >= 0; i--) {
            try {
                JSONObject commitDetails = commits.getJSONObject(i);
                JSONObject commit = commitDetails.getJSONObject("commit");
                JSONObject author = commitDetails.getJSONObject("author");

                String id = commitDetails.getString("sha");
                String message = commit.getString("message");
                String user = author.getString("login");

                Date date = DateTimeFormat.parse(commit.getJSONObject("author").getString("date"));

                GitHubUser ghUser = ghUsers.get(user);

                if (ghUser == null) {
                    String accountUrl = author.getString("html_url");
                    String avatarUrl = author.getString("avatar_url");

                    ghUser = new GitHubUser(user, accountUrl, avatarUrl);
                    ghUsers.put(user, ghUser);
                }

                // TODO work out a easy way to get files or remove it
                GitHubCommit ghCommit = new GitHubCommit(id, date, message, null, ghUser);

                gitHubCommits.put(date, ghCommit);
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Error parsing JSONObject", ex);
                //Skip this one and carry on
            }
        }

        //Uncomment for fake commits
        gitHubCommits.put(
                DateTimeFormat.parse(DateTimeFormat.format(new Date())),
                new GitHubCommit("1f1d8f711b4258e38825083a2db401862602c14b",
                        new Date(),
                        "Some bogus message that has some weight to it",
                        null,
                        new GitHubUser("FakeUser", "#", "#")));

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
        try {
            JSONObject user = new JSONObject(call(Path.user(userName)));
            return new GitHubUser(
                    user.getString("login"),
                    user.getString("html_url"),
                    user.getString("avatar_url")
            );
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, "Error getting user", ex);
            return null;
        }

    }

    /**
     * Makes a call to the githubAPI without passing in header variables in.
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

    /**
     * Makes a call to the githubAPI and calls all pages returned through the
     * response header Link
     *
     * @param path The URL to call
     * @param parameter Extra parameters used
     * @return
     */
    private JSONArray callForAllPages(String path, Map<String, String> parameter) {
        Collection<JSONArray> collection = new ArrayList<>();

        while (true) {
            // Path is passed in at the start but is 
            // changed at the end of this loop
            WebTarget webTarget = ClientBuilder.newClient().target(path);

            if (parameter != null) {

                for (Map.Entry<String, String> entry : parameter.entrySet()) {
                    webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
                }
            }

            Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", " token " + token)
                    .get();

            // Response Data
            String data = response.readEntity(String.class);

            if (data.equals("[]")) {
                break; // no data
            }
            // Valid data
            collection.add(new JSONArray(data));

            // check to see if there is any more data in response headers
            MultivaluedMap<String, String> stringHeaders = response.getStringHeaders();

            if (stringHeaders == null || !stringHeaders.containsKey("Link")) {
                break;
            }

            String newURL = stringHeaders.get("Link").get(0).split(",")[0];

            // 'Next' indicates there is more data to be fetched
            if (!newURL.contains("next")) {
                break; // No more data to get
            }

            // Get path out of string
            int start = newURL.indexOf("<");
            int end = newURL.indexOf(">");
            path = newURL.substring(start + 1, end - start);
        }
        // Have to loop through and merge into one jsonArray
        JSONArray array = new JSONArray();

        for (JSONArray jSONArray : collection) {
            for (int i = 0; i < jSONArray.length(); i++) {
                array.put(jSONArray.get(i));
            }
        }

        return array;
    }

}
