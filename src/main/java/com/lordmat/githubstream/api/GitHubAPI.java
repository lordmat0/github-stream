/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lordmat.githubstream.api;

import org.json.JSONObject;

/**
 *
 * @author mat
 */
public class GitHubAPI {
    
    public static JSONObject jsonObject;

    public GitHubAPI() {
        
        GitHubCaller call = new GitHubCaller();
        
        jsonObject = call.getPaths();

    }
    
    
    
}
