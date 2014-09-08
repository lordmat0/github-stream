/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lordmat.githubstream.page;

import com.lordmat.githubstream.api.GitHubCommit;
import com.lordmat.githubstream.web.StartManager;
import java.util.Date;
import java.util.NavigableMap;

/**
 * This class will handle loading the page on refresh, it caches the commits
 * in a StringBuilder and only adds new ones to the list. This class will also handle
 * adding html tags and classes.
 * @author mat
 */
public class PageManager {
    private final static StringBuilder page = new StringBuilder();
    
    private final static NavigableMap<Date, GitHubCommit> commitList = StartManager.gitHubAPI.getCommits();
    private static Date lastDate;
    
    public static String makePage(){
        
        if(lastDate == null || !lastDate.equals(commitList.lastEntry().getKey())){
            for(NavigableMap.Entry<Date, GitHubCommit> entry : commitList.entrySet()){
                if(entry.getKey().equals(lastDate)){
                    break;
                }
                
                // todo sanitize getValue()
                page.insert(0, entry.getValue());
                page.insert(0, "<p>");
            }
            
            lastDate = commitList.lastKey();
        }

        return page.toString();
    }
    
}
