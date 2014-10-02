package com.lordmat.githubstream.data.checker;

import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.data.GitHubCaller;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class extends AbstractChecker to implement the query method. This object
 * checks the GitHubApi for a branch list, it then creates worker threads that
 * query GitHubApi with the branch information. The number of branches
 * corresponds in terms of minutes for this to happen, for example, if there are
 * 5 branches then this will run every 5 minutes. The map passed in is
 * thread-safe
 *
 * @author mat
 */
public class BranchChecker extends AbstractChecker {

    private final static Logger LOGGER = Logger.getLogger(CommitChecker.class.getName());

    private final GitHubCaller caller;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final Map<String, GitHubBranch> branches;

    private final ExecutorService threadHandler;

    public BranchChecker(NavigableMap<Date, GitHubCommit> gitHubCommits, Map<String, GitHubBranch> branches) {
        super(300_000); // 5 minutes default
        this.branches = branches;
        this.gitHubCommits = gitHubCommits;

        caller = new GitHubCaller();

        threadHandler = Executors.newCachedThreadPool();
    }

    @Override
    protected void query() {
        try {
            // Need to perform more than one operation so need to it synchronized
            synchronized (branches) {
                Map<String, GitHubBranch> newBranches = caller.getBranches();

                // Start checking commits in new branches 
                for (Map.Entry<String, GitHubBranch> branchEntry : newBranches.entrySet()) {
                    String branchName = branchEntry.getKey();

                    GitHubBranch branch = branchEntry.getValue();
                    
                    GitHubBranch oldBranch = branches.get(branch.getName());
                    
                    // If it already exists set the commit list to the old one
                    if(oldBranch != null){
                        branch.setCommits(oldBranch.getCommits());
                    }
                    
                    threadHandler
                            .submit(new CommitChecker(gitHubCommits, branch));

                    branches.put(branchName, branch);
                }

                branches.clear();
                branches.putAll(newBranches);

                setQueryTime(newBranches.size() * 60);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "BranchChecker threw an error, re-trying", ex);
        }

    }

}
