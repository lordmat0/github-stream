/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.data.GitHubCaller;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mat
 */
public class BranchChecker extends AbstractChecker{

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final Map<String, GitHubBranch> branches;

    public BranchChecker(Map<String, GitHubBranch> branches) {
        super(300_000); // 5 minutes
        this.branches = branches;
        caller = new GitHubCaller();
    }
    
    
    

    @Override
    protected void query() {
        try {
            // Need to perform more than one operation so need to it synchronized
            synchronized (branches) {
                Map<String, GitHubBranch> newBranches = caller.getBranches();
                branches.clear();
                branches.putAll(newBranches);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "BranchChecker threw an error, re-trying", ex);
        }

    }

}
