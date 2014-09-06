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

    public GitHubCaller() {
        ResourceBundle bundle = ResourceBundle.getBundle("project");
        token = bundle.getString("authtoken");
    }

    public JSONObject getPaths() {
        return new JSONObject(call(GitHubPath.DEFAULT_PATH));
    }

    public JSONObject getRateLimit() {
        return new JSONObject(call(GitHubPath.RATE_LIMIT));
    }

    public JSONArray getCommits(String since, String until) {
        Map<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("since", since);
        queryParam.put("until", until);
        
        return new JSONArray(call(GitHubPath.COMMITS, queryParam));
    }

    public GitHubUser getUser(String userName) {
        JSONObject user = new JSONObject(call(GitHubPath.user(userName)));
        return new GitHubUser(
                user.getString("login"),
                user.getString("url"),
                user.getString("avatar_url")
        );
    }

    private String call(String path) {
        return call(path, null);
    }

    private String call(String path, Map<String, String> parameter) {
        WebTarget webTarget = ClientBuilder.newClient().target(path);

        if (parameter != null) {
            for (String key : parameter.keySet()) {
                webTarget = webTarget.queryParam(key, parameter.get(key));
            }
        }

        return webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", " token " + token)
                .get(String.class);

    }

}
