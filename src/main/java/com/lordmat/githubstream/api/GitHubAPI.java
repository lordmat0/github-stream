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
public class GitHubAPI {
    
    public static String results = "Empty";
    public static JSONObject jsonObject;

    public GitHubAPI() {
        
        results = ClientBuilder.newClient().target("https://api.github.com")
                .queryParam("foo", "bar")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        
        
        jsonObject = new JSONObject(results);
        
        
       
        System.out.println("Results from github are\n" + results);
    }
    
    
    
}
