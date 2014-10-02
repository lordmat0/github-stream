package com.lordmat.githubstream.data;

import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.data.checker.BranchChecker;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;

/**
 * This class provides access to github users stored and commits.
 *
 * @author mat
 */
public class GitHubData {

    private final static Logger LOGGER = Logger.getLogger(GitHubData.class.getName());

    private final Map<String, GitHubUser> gitHubUsers;
    private final NavigableMap<Date, GitHubCommit> gitHubCommits;
    private final Map<String, GitHubBranch> branches;

    private final Map<String, Date> branchToCommit;

    private final GitHubCaller caller;

    /**
     * Set to true if all the commits from the repo have been cached inside
     * gitHubCommits, this is used by the getOldCommits method
     */
    private boolean hasLastCommit;

    public GitHubData() {
        gitHubUsers = new ConcurrentHashMap<>();
        gitHubCommits = new ConcurrentSkipListMap<>();
        caller = new GitHubCaller();
        branches = new ConcurrentHashMap<>();

        branchToCommit = new ConcurrentHashMap<>();

        new BranchChecker(gitHubCommits, branches).start();
    }

    /**
     * Returns an unmodifiable thread safe NavigableMap, sorted by keys map
     * containing commits.
     *
     * @return List of commits
     */
    public NavigableMap<Date, GitHubCommit> getCommits() {
        return Collections.unmodifiableNavigableMap(gitHubCommits);
    }

    /**
     * Returns a thread safe map containing all users
     *
     * @return
     */
    public Map<String, GitHubUser> getUsers() {
        return gitHubUsers;
    }

    /**
     * Returns a list of GitHubUsers that are found
     *
     * @param users Users to find
     * @return Found users, users not found are not returned
     */
    public List<GitHubUser> getUsers(List<String> users) {

        List<GitHubUser> returnedUsers = new ArrayList<>();
        for (String userName : users) {

            GitHubUser user = gitHubUsers.get(userName);

            // User returns null if it doesn't exist and not used in any commits
            if (user == null) {
                continue;
            }

            returnedUsers.add(user);
        }

        return returnedUsers;
    }

    /**
     * This returns the top 25 commits in the list currently, The top is defined
     * as the newest commits. If there are less than 25 commits than all the
     * commits will be returned.
     *
     * @param branchName The branch name to get the commits from, null for all
     * commits
     * @return A list of the newest github commits
     */
    public List<GitHubCommit> getTopCommits(String branchName) {
        List<GitHubCommit> commitList = new ArrayList<>(25);

        GitHubBranch branch = null;
        if (branchName != null) {
            branch = branches.get(branchName);
        }

        if (branch != null) {
            TreeSet<Date> dates = branch.getCommits();

            {
                Iterator iter = dates.descendingIterator();

                for (int i = 0; iter.hasNext() && i < 25; i++) {
                    commitList.add(gitHubCommits.get((Date) iter.next()));
                }

            }

        } else {
            {
                Iterator iter = gitHubCommits.descendingMap().values().iterator();

                for (int i = 0; iter.hasNext() && i < 25; i++) {
                    commitList.add((GitHubCommit) iter.next());
                }
            }
        }

        return commitList;
    }

    /**
     * Checks for new commits, if the date passed in null or empty then a empty
     * list is returned
     *
     * @param latestCommitDate The commit date to check against
     * @return An empty list or commits that come after the lastestCommitId
     *
     */
    // TODO change to a set
    public List<GitHubCommit> getNewCommits(String latestCommitDate) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (latestCommitDate == null || latestCommitDate.isEmpty()) {
            return newCommits;
        }

        Date date = DateTimeFormat.parse(latestCommitDate);

        if (gitHubCommits.isEmpty()) {
            LOGGER.info("Githubcommit list is empty");
            return newCommits;
        }

        if (gitHubCommits.lastKey().equals(date)) {
            return newCommits; // no new commits
        }

        // get a subset of the list
        newCommits.addAll(gitHubCommits.tailMap(date, false).values());

        return newCommits;
    }

    /**
     * Checks for new commits, if the date or branch name passed in null/empty
     * then a empty list is returned
     *
     * @param latestCommitDate The commit date to check against
     * @param branchName The branch to get commits from
     * @return
     */
    //TODO change to a set
    public List<GitHubCommit> getNewCommits(String latestCommitDate, String branchName) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        if (latestCommitDate == null || latestCommitDate.isEmpty()
                || branchName == null || branchName.isEmpty()) {
            return newCommits;
        }

        Date date = DateTimeFormat.parse(latestCommitDate);
        TreeSet<Date> dateSet = null;

        GitHubBranch branch = branches.get(branchName);

        if (branch == null) {
            // Can't find branch
            LOGGER.warning("Could not find branch " + branchName);
            return newCommits;
        } else {
            dateSet = branch.getCommits();
        }

        if (dateSet.isEmpty()) {
            LOGGER.info(branchName + " has no commits in getNewCommits");
            return newCommits;
        }

        if (gitHubCommits.lastKey().equals(date)) {
            // no new commits
            return newCommits;
        }

        // Get a subset of the list
        Iterator<Date> iter = dateSet.tailSet(date, false).iterator();

        // Only add dates in the branch to newCommits
        while (iter.hasNext()) {
            newCommits.add(gitHubCommits.get(iter.next()));
        }

        return newCommits;
    }

    /**
     * I believe this needs to be synchronized because otherwise two requests
     * would mean that it could be calling getCommits twice on the same date
     * wasting bandwidth
     *
     * @param earlistCommitDate The earlist commit date
     * @return
     */
    //TODO make this get old commits for all branches
    public synchronized List<GitHubCommit> getOldCommits(String earlistCommitDate) {
        Set<GitHubCommit> newCommits = new TreeSet<>();

        if (earlistCommitDate == null || earlistCommitDate.isEmpty()
                || gitHubCommits.isEmpty()) {
            return new ArrayList<>(newCommits);
        }

        for (String branchName : branches.keySet()) {
            List<GitHubCommit> commits = getOldCommits(earlistCommitDate, branchName);
            System.out.println(commits.size());

        }
        Date date = DateTimeFormat.parse(earlistCommitDate);

        // get a subset of the list
        newCommits.addAll(gitHubCommits.headMap(date).values());

        List<GitHubCommit> newCommitList = new ArrayList<>(newCommits);
        Collections.reverse(newCommitList);

        // Limit size to 30
        int newCommitsSize = newCommitList.size();
        return newCommitList.subList(0, (newCommitsSize >= 30 ? 30 : newCommitsSize));
    }

    /**
     * I believe this needs to be synchronized because otherwise two requests
     * would mean that it could be calling getCommits twice on the same date
     * wasting bandwidth
     *
     * @param earlistCommitDate The earlist commit date
     * @param branchName The branch to get commits from
     * @return
     */
    public synchronized List<GitHubCommit> getOldCommits(String earlistCommitDate, String branchName) {
        List<GitHubCommit> newCommits = new ArrayList<>();

        GitHubBranch branch = branches.get(branchName);

        if (earlistCommitDate == null || earlistCommitDate.isEmpty()
                || branch == null) {
            return newCommits;
        }

        TreeSet<Date> branchDates = branch.getCommits();

        if (branchDates.isEmpty()) {
            LOGGER.info(branchName + " has no commits in getOldCommits");
            return newCommits;
        }

        Date date = DateTimeFormat.parse(earlistCommitDate);

        if (!branch.getHasLastCommit()
                && (branchDates.first().equals(date) || branchDates.first().before(date))) {

            date = branchDates.first();

            // If we don't contain the date assume we need to get more commits,
            // but don't trust the client date passed in
            NavigableMap<Date, GitHubCommit> mapCommits = caller.getCommits(null,
                    DateTimeFormat.format(date), branch.getSha());

            if (mapCommits.firstKey().equals(date)) {
                branch.setHasLastCommit(true);
            }

            // Add the new commits to the branch
            branchDates.addAll(mapCommits.keySet());

            gitHubCommits.putAll(mapCommits);
        }

        Iterator iter = branchDates.headSet(date, false).descendingIterator();

        while (iter.hasNext()) {
            newCommits.add(gitHubCommits.get((Date) iter.next()));
        }

        // Limit size to 30
        int newCommitsSize = newCommits.size();
        return newCommits.subList(0, (newCommitsSize >= 30 ? 30 : newCommitsSize));
    }

    public Map<String, GitHubBranch> getBranches() {
        return new HashMap<>(branches);
    }

}
