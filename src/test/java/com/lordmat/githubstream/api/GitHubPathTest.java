/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.api;

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
public class GitHubPathTest {

    public GitHubPathTest() {
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

    @Test
    public void testDefaultPath() {
        System.out.println("testDefaultPath");
        assertTrue(GitHubPath.DEFAULT_PATH.length() > 0);
    }

    @Test
    public void testRateLimit() {
        System.out.println("testRateLimit");
        assertTrue(GitHubPath.RATE_LIMIT.length() > 0);
    }

    @Test
    public void testRepoName() {
        System.out.println("testRepoName");
        assertTrue(GitHubPath.REPO_NAME.length() > 0);
    }

    @Test
    public void testRepoOwner() {
        System.out.println("testRepoOwner");
        assertTrue(GitHubPath.REPO_OWNER.length() > 0);
    }

    @Test
    public void testCommits() {
        System.out.println("testCommits");
        assertTrue(GitHubPath.COMMITS.length() > 0);
    }

    @Test
    public void testUserUrl() {
        System.out.println("testUserUrl");
        assertTrue(GitHubPath.USER_URL.length() > 0);
    }

}
