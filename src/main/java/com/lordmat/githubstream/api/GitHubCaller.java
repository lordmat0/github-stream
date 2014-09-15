package com.lordmat.githubstream.api;

import com.lordmat.githubstream.resource.MyResourceBundle;
import com.lordmat.githubstream.resource.ResourceKey;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
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
    public Map<Date, GitHubCommit> getCommits(String since, String until) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("since", since);
        queryParam.put("until", until);

        JSONArray commits = callAllPages(Path.REPO_COMMITS, queryParam);

        Map<Date, GitHubCommit> gitHubCommits = new LinkedHashMap<>();

        // Check results
        if (commits.length() == 0) {
            LOGGER.fine("No commits retrieved");
        } else if (!commits.getJSONObject(0).has("commit")) {
            LOGGER.warning("getCommits did not receive a"
                    + " list of commits (maybe a 404?)");
        }

        for (int i = commits.length() - 1; i >= 0; i--) {

            JSONObject commitDetails = commits.getJSONObject(i);
            JSONObject commit = commitDetails.getJSONObject("commit");
            JSONObject author = commit.getJSONObject("author");

            String id = commitDetails.getString("sha");
            String message = commit.getString("message");
            String user = author.getString("name");

            Date date = DateTimeFormat.parse(author.getString("date"));

            //TODO work out a easy way to get files or remove it
            GitHubCommit ghCommit = new GitHubCommit(id, date, message, null, user);

            gitHubCommits.put(date, ghCommit);
        }

        //Uncomment for fake commits
        gitHubCommits.put(
                DateTimeFormat.parse(DateTimeFormat.format(new Date())),
                new GitHubCommit("fake", new Date(), "fake", null, "fake"));

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

    private JSONArray callAllPages(String path, Map<String, String> parameter) {
        Collection<JSONArray> collection = new ArrayList<>();

        while (true) {
            WebTarget webTarget = ClientBuilder.newClient().target(path);
            if (parameter != null) {

                for (Map.Entry<String, String> entry : parameter.entrySet()) {
                    webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
                }
            }

            Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", " token " + token)
                    .get();
            String data = response.readEntity(String.class);

            if(data.equals("[]")){
                break; // no data
            }
            
            String[] urlArray  = response.getStringHeaders().get("Link").get(0).split(",");

            String newURL = urlArray[0];
            
            if (!newURL.contains("next")) {
                break;
            }
            // Valid URL
            int start = newURL.indexOf("<");
            int end = newURL.indexOf(">");

            path = newURL.substring(start + 1, end - start);
            
           
            collection.add(new JSONArray(data));
        }
        // Have to loop through and merge into one jsonArray
        JSONArray array = new JSONArray();
        
        for(JSONArray jSONArray : collection){
            for(int i = 0; i < jSONArray.length(); i++){
                array.put(jSONArray.get(i));
            }
        }

        

        return array;
    }
}
