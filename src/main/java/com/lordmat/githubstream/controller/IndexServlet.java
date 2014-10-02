package com.lordmat.githubstream.controller;

import com.lordmat.githubstream.bean.GitHubCommit;
import com.lordmat.githubstream.StartManager;
import com.lordmat.githubstream.bean.GitHubBranch;
import com.lordmat.githubstream.resource.Path;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mat
 */
@WebServlet(urlPatterns = {"/index.jsp"}, loadOnStartup = 1)
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, GitHubBranch> branches = StartManager.data().getBranches();

        String branchName = request.getParameter("branch");

        if (branchName == null || (!branches.isEmpty() && !branches.containsKey(branchName))) {

            // Check if there is a master branch
            branchName = branches.containsKey("master") || branches.isEmpty() ? "master" : branches.keySet().iterator().next();

            response.sendRedirect("?branch=" + branchName);
            return;
        }

        List<GitHubCommit> commits = StartManager.data().getTopCommits(branchName);

        request.setAttribute("commits", commits);
        request.setAttribute("commitsLength", commits.size());

        request.setAttribute("project", Path.REPO_NAME);
        request.setAttribute("owner", Path.REPO_OWNER);
        request.setAttribute("projectUrl", Path.REPO_URL);

        request.setAttribute("branchName", branchName);
        request.setAttribute("branches", branches);
        request.setAttribute("branchesLength", branches.size());

        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }
}
