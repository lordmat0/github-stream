/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lordmat.githubstream.api;

import java.util.ResourceBundle;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mat
 */
public class GitHubCaller {
    
    private final String token;
    
    public GitHubCaller(){
        ResourceBundle bundle = ResourceBundle.getBundle("project"); 
        token = bundle.getString("authtoken");
    }
   
    public JSONObject getPaths(){
        return new JSONObject(call(""));
    }
    
    public JSONObject getRateLimit(){
        return new JSONObject(call("rate_limit"));
    }
    
    public JSONArray getCommits(){
        return new JSONArray(call("repos/lordmat0/githubstream/commits"));
    }
    
    
    private String call(String path){
        return ClientBuilder.newClient().target("https://api.github.com/" + path)
                //.queryParam("foo", "bar")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", " token " + token)
                .get(String.class);
    }
    
}
