/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lordmat.githubstream.controller;

import com.lordmat.githubstream.api.GitHubCommit;
import com.lordmat.githubstream.web.StartManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mat
 */
@WebServlet(urlPatterns ={"/index", "/.", "/index.jsp"})
public class IndexServlet extends HttpServlet {
    //TODO redirect /GitHubStream to this Servlet
    
    private List<GitHubCommit> commits;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //TODO get only the top 25 commits
        commits = new ArrayList<>(StartManager.gitHubAPI.getCommits().descendingMap().values());
        
        request.setAttribute("commits", commits);
        
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }
}
