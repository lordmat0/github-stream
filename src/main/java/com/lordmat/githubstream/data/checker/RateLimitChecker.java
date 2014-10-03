/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.bean.GitHubRateLimit;
import com.lordmat.githubstream.data.GitHubCaller;
import java.util.logging.Logger;

/**
 *
 * @author mat
 */
public class RateLimitChecker extends AbstractChecker{

    private final static Logger LOGGER = Logger.getLogger(RateLimitChecker.class.getName());
    private final GitHubCaller gitHubCaller;
    
    public RateLimitChecker() {
        super(120_000); // 2 minutes
        gitHubCaller = new GitHubCaller();
    }

    @Override
    protected void query() {
        GitHubRateLimit gitHubRateLimit = gitHubCaller.getRateLimit();
        
        LOGGER.info("Current Rate Limit is: " + gitHubRateLimit);
    }
    
}
