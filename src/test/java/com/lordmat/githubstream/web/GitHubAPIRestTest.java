/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.web;

import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.bean.GitHubUser;
import com.lordmat.githubstream.bean.StringBean;
import com.lordmat.githubstream.bean.UserList;
import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

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
    public void setUp() throws InterruptedException {
        // Need to create a start Manager to set up static variables
        StartManager startManager = new StartManager();

        // Need to have some commits before testing on them
        for (int i = 0; i < 5; i++) {
            if (!StartManager.data().getCommits().isEmpty()) {
                break;
            }
            Thread.sleep(1000);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getUsers method, of class GitHubAPIRest.
     */
    @Test
    public void testGetAllValidUsers() {
        System.out.println("getUsers getValidUsers");

        List<String> users = new ArrayList<>();
        users.add("lordmat0");
        users.add("shobute");
        users.add("Mottie");

        UserList userList = new UserList();
        userList.setUsers(users);

        GitHubAPIRest instance = new GitHubAPIRest();

        //List<GitHubUser> expResult = null;
        List<GitHubUser> result = instance.getUsers(userList);

        assertTrue(result.size() == 1);
        assertNotNull(result.get(0));
    }

    /**
     * Test of getUsers method, of class GitHubAPIRest.
     */
    @Test
    public void testGetAllWrongUsers() {
        System.out.println("getUsers allWrongUsers");

        List<String> users = new ArrayList<>();

        users.add("doesnt_exist_user_01");
        users.add("doesnt_exist_user_02");
        users.add("doesnt_exist_user_03");

        UserList userList = new UserList();
        userList.setUsers(users);

        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubUser> result = instance.getUsers(userList);

        // Should be empty because no users exist
        assertTrue(result.isEmpty());
    }

    /**
     * Test of getUsers method, of class GitHubAPIRest.
     */
    @Test
    public void testGetSomeWrongUsers() {
        System.out.println("getUsers someWrongUsers");

        List<String> users = new ArrayList<>();
        users.add("lordmat0");
        users.add("doesnt_exist_user_02");
        users.add("doesnt_exist_user_03");

        UserList userList = new UserList();
        userList.setUsers(users);

        GitHubAPIRest instance = new GitHubAPIRest();

        //List<GitHubUser> expResult = null;
        List<GitHubUser> result = instance.getUsers(userList);

        // Only one user should have been added since the others don't exist
        assertTrue(result.size() == 1);
    }

    @Test
    public void testSingleUser() {
        System.out.println("getUsers singleUser");

        List<String> users = new ArrayList<>();
        users.add("lordmat0");

        UserList userList = new UserList();
        userList.setUsers(users);

        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubUser> result = instance.getUsers(userList);

        GitHubUser mat = new GitHubUser("lordmat0", // user name
                "https://github.com/lordmat0", // account url
                "https://avatars.githubusercontent.com/u/4976353?v=2"); // avatar url

        assertEquals(mat, result.get(0));
    }

    @Test
    public void testSingleWrongUser() {
        System.out.println("getUsers singleWrongUser");

        List<String> users = new ArrayList<>();
        users.add("doesnt_exist_user_01");

        UserList userList = new UserList();
        userList.setUsers(users);

        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubUser> result = instance.getUsers(userList);

        assertTrue(result.isEmpty());
    }

    /**
     * Test of getNewCommits method, of class GitHubAPIRest.
     */
    @Test
    public void testGetNewCommits() {
        System.out.println("getNewCommits");

        // always has a few commits ahead of this ID
        // TODO auto-generate this date
        String latestCommitDate = "2014-09-25T20:45:35Z";
        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubCommit> result = instance.getNewCommits(latestCommitDate, null);

        Date date = DateTimeFormat.parse(latestCommitDate);

        assertTrue(date.before(result.get(result.size() - 1).getDate()));

        assertTrue(!result.isEmpty());
    }

    /**
     * Test of getNewCommits method, of class GitHubAPIRest.
     */
    @Test
    public void testGetNewCommitsNullDate() {
        System.out.println("getNewCommits");

        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubCommit> result = instance.getNewCommits(null, null);

        assertTrue(result.isEmpty());
    }

    /**
     * Test of getNewCommits method, of class GitHubAPIRest.
     */
    @Test
    public void testGetNewCommitsEmptyDate() {
        System.out.println("getNewCommits");

        // always has a few commits ahead of this ID
        String emptyString = "";
        GitHubAPIRest instance = new GitHubAPIRest();

        List<GitHubCommit> result = instance.getNewCommits(emptyString, null);

        assertTrue(result.isEmpty());
    }

    /**
     * Test of getOldCommits method, of class GitHubAPIRest.
     */
    @Test
    public void testGetOldCommitsAlreadyCached() {
        System.out.println("testGetOldCommitsAlreadyCached");

        // always has a few commits before of this ID
        String earlistCommitId = "2014-09-20T19:58:39Z"; // 2014-09-12T19:58:39
        GitHubAPIRest instance = new GitHubAPIRest();

        Collection<GitHubCommit> result = instance.getOldCommits(new StringBean(earlistCommitId));

        assertTrue(result.size() > 0);
    }

    /**
     * Test of getOldCommits method, of class GitHubAPIRest.
     */
    @Ignore
    public void testGetOldCommitsNotCached() {
        System.out.println("testGetOldCommitsNotCached");

        // always has a few commits before of this ID
        String earlistCommitId = "2014-09-02T23:12:59Z";
        GitHubAPIRest instance = new GitHubAPIRest();

        Collection<GitHubCommit> result = instance.getOldCommits(new StringBean(earlistCommitId));

        assertTrue(result.size() > 0);
    }

    /**
     * Test of getOldCommits method, of class GitHubAPIRest.
     *
     * I used this test method to get all the commits to make sure result4 is
     * empty since all the commits have already been got. This test will fail
     * when there are more commits
     *
     */
    @Ignore
    public void testGetAllCommits() {
        System.out.println("testGetAllCommits");

        // always has a few commits before of this ID
        String earlistCommitId = "2014-09-01T20:22:36Z";
        GitHubAPIRest instance = new GitHubAPIRest();
        
        Collection<GitHubCommit> result = instance.getOldCommits(new StringBean(earlistCommitId));
        Collection<GitHubCommit> result2 = instance.getOldCommits(new StringBean(earlistCommitId));
        Collection<GitHubCommit> result3 = instance.getOldCommits(new StringBean(earlistCommitId));
        Collection<GitHubCommit> result4 = instance.getOldCommits(new StringBean(earlistCommitId));

        assertTrue(result4.isEmpty());
    }

}
