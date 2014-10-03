/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data;

import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.bean.GitHubRateLimit;
import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mat
 */
public class GitHubCallerTest {

    public GitHubCallerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Need to init to create GitHubData
        new StartManager();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPaths method, of class GitHubCaller.
     */
    @Test
    public void testGetPaths() {
        System.out.println("getPaths");
        GitHubCaller instance = createInstance();
        JSONObject result = instance.getPaths();

        // Testing to make sure all currently used exist
        assertNotNull(result.get("current_user_repositories_url"));
        assertNotNull(result.get("rate_limit_url"));
        assertNotNull(result.get("repository_url"));

    }

    /**
     * Test of getRateLimit method, of class GitHubCaller.
     */
    @Test
    public void testGetRateLimit() {
        System.out.println("getRateLimit");
        GitHubCaller instance = createInstance();

        GitHubRateLimit result = instance.getRateLimit();

        assertNotNull(result.getLimit());
        assertNotNull(result.getRemaining());
        assertNotNull(result.getReset());

        // If it fails here then no GitHub requests can be made
        assertEquals(5000, result.getLimit());
        assertNotEquals(0, result.getRemaining());
    }

    @Test
    public void testGetCommits() {
        System.out.println("testGetCommits");
        GitHubCaller instance = createInstance();

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.DATE, calender.getActualMinimum(Calendar.DATE));

        String since = DateTimeFormat.format(calender.getTime());
        String until = null;

        Map<Date, GitHubCommit> result = instance.getCommits(since, until);

        assertTrue(result.size() > 0);

        assertNotNull(result.values().iterator().next().getDate());
    }

    @Test
    public void testGetUser() {
        System.out.println("testGetUser");
        GitHubCaller instance = createInstance();

        GitHubUser gitHubUser = instance.getUser("lordmat0");

        assertNotNull(gitHubUser);
    }

    @Test
    public void testGetBranches() {
        System.out.println("testGetBranches");
        GitHubCaller instance = createInstance();

        Map<String, GitHubBranch> branches = instance.getBranches();

        assertTrue(!branches.isEmpty());
        assertTrue(branches.containsKey("master"));
    }

    @Test
    public void testGetCommitsFromBranch() {
        System.out.println("testGetCommitsFromBranch");
        GitHubCaller instance = createInstance();

        Map<String, GitHubBranch> branches = instance.getBranches();

        String sha = branches.get("develop").getSha();

        // My project always has a develop branch
        // but others may not so just get a random branch
        if (sha == null) {
            sha = branches.get(new ArrayList<>(branches.keySet()).get(0)).getSha();
        }

        Map<Date, GitHubCommit> result = instance.getCommits(null, null, sha);

        assertTrue(!result.isEmpty());
    }

    @Test
    public void testGetCommitsFromUnknownBranch() {
        System.out.println("testGetCommitsFromUnknownBranch");
        GitHubCaller instance = createInstance();

        String sha = "NOPE_BRANCH_LOL";

        Map<Date, GitHubCommit> result = instance.getCommits(null, null, sha);

        assertTrue(result.isEmpty());
    }
    
    /**
     * Creates an instance of github caller which is wrapped in a try catch with
     * an extra message if it fails
     *
     * @return new instance of GitHubCaller
     */
    private GitHubCaller createInstance() {
        try {
            return new GitHubCaller();
        } catch (Exception ex) {
            fail("Failed to create GitHubCaller, "
                    + "properly an issue with package location: " + ex.getMessage());
            throw ex;
        }
    }
}
