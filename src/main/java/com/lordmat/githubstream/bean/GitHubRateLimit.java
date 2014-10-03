/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.bean;

import com.lordmat.githubstream.util.DateTimeFormat;
import java.util.Date;

/**
 *
 * @author mat
 */
public class GitHubRateLimit {

    private int limit;
    private int remaining;
    private Date reset;

    public GitHubRateLimit(int limit, int remaining, Date reset) {
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
    }

    @Override
    public String toString() {
        return "limit: " + limit + ", remaining: " + remaining + ", reset: " + DateTimeFormat.formatTime(reset);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public Date getReset() {
        return reset;
    }

    public void setReset(Date reset) {
        this.reset = reset;
    }

}
