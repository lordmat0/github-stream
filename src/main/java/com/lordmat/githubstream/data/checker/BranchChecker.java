/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.data.GitHubCaller;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class extends AbstractChecker to implement the query method. This object
 * will check the GitHubApi every 5 minutes to get an updated branch list. The
 * map passed in is thread-safe
 *
 * @author mat
 */
public class BranchChecker extends AbstractChecker {

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final Map<String, GitHubBranch> branches;
    private final Map<String, Future<?>> threads;

    private final ExecutorService threadHandler;

    public BranchChecker(NavigableMap<Date, GitHubCommit> gitHubCommits, Map<String, GitHubBranch> branches) {
        super(300_000); // 5 minutes
        this.branches = branches;
        this.gitHubCommits = gitHubCommits;

        caller = new GitHubCaller();
        threads = new HashMap<>();

        threadHandler = Executors.newCachedThreadPool();
    }

    @Override
    protected void query() {
        try {
            // Need to perform more than one operation so need to it synchronized
            synchronized (branches) {
                /**
                 * TODO make sure that old branches aren't being removed when
                 * infact they have just been pushed futher since if the SHA
                 * changes this will mean that we need to re-generate the date
                 * list which is just stupid as I've tested and you can have a
                 * branch and test any range of dates on it
                 */
                Map<String, GitHubBranch> newBranches = caller.getBranches();

                // Start checking commits in new branches 
                for (Map.Entry<String, GitHubBranch> branchEntry : newBranches.entrySet()) {
                    String branchName = branchEntry.getKey();

                    if (!branches.containsKey(branchName)) {
                        String sha = branchEntry.getValue().getSha();

                        Future<?> newThread = threadHandler
                                .submit(new CommitChecker(gitHubCommits, sha));

                        threads.put(branchName, newThread);
                        branches.put(branchName, branchEntry.getValue());
                    }
                }

                // Stop old threads running if they're not in newBranches map
                for (Iterator<Map.Entry<String, GitHubBranch>> it
                        = branches.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, GitHubBranch> entry = it.next();
                    
                    String branchName = entry.getKey();
                    
                    // If it doesn't contain it in the newBranch 
                    if(!newBranches.containsKey(branchName)){
                        // Stop thread
                        threads.get(branchName).cancel(true);
                                
                        // Remove it from map
                        it.remove();
                    }
                    
                }
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "BranchChecker threw an error, re-trying", ex);
        }

    }

}
