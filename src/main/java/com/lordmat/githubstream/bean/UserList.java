package com.lordmat.githubstream.bean;

import java.util.List;

/**
 * Java bean for sending a list of users
 *
 * @author mat
 */
public class UserList {

    private List<String> users;

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
