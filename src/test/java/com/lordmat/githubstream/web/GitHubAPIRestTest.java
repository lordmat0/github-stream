/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.web;

import com.lordmat.githubstream.api.GitHubCommit;
import com.lordmat.githubstream.api.GitHubUser;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author mat
 */
public class GitHubAPIRestTest {

    public GitHubAPIRestTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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
     * Test of getUsers method, of class GitHubAPIRest.
     */
    @Ignore
    public void testGetUsers() {
        System.out.println("getUsers");

        // TODO add more users to test
        String[] users = new String[]{"lordmat0", "foo", "func"};
        GitHubAPIRest instance = new GitHubAPIRest();

        //List<GitHubUser> expResult = null;
        List<GitHubUser> result = instance.getUsers(users);

        assertTrue(result.size() == 3);

    }

    @Ignore
    public void testSingleUser() {
        System.out.println("getUsers (test2)");

        String[] users = new String[]{"lordmat0",};
        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubUser> result = instance.getUsers(users);

        // TODO find lordmat0 user details
        GitHubUser mat = new GitHubUser("lordmat0", "?", "?");
        assertEquals(mat, result.get(0));
    }

    /**
     * Test of getNewCommits method, of class GitHubAPIRest.
     */
    @Ignore
    public void testGetNewCommits() {
        System.out.println("getNewCommits");

        // TODO find ID that has a few commits after it
        // always has a few commits ahead of this ID
        String lasestCommitId = "";
        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubCommit> result = instance.getNewCommits(lasestCommitId);

        assertTrue(result.size() > 0);
    }

    /**
     * Test of getOldCommits method, of class GitHubAPIRest.
     */
    @Ignore
    public void testGetOldCommits() {
        System.out.println("getOldCommits");

        // TODO find ID that has a few commits before it
        // always has a few commits before of this ID
        String earlistCommitId = "";
        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubCommit> result = instance.getOldCommits(earlistCommitId);

        assertTrue(result.size() > 0);
    }

}
