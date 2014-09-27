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
@WebServlet(urlPatterns = {"/index", "/.", "/index.jsp"}, loadOnStartup = 1)
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<GitHubCommit> commits = StartManager.data().getTopCommits();
        Map<String, GitHubBranch> branches = StartManager.data().getBranches();

        request.setAttribute("commits", commits);
        request.setAttribute("commitsLength", commits.size());
        
        request.setAttribute("project", Path.REPO_NAME);
        request.setAttribute("owner", Path.REPO_OWNER);
        request.setAttribute("projectUrl", Path.REPO_URL);
        
        // Remove the master from the list
        GitHubBranch masterBranch = branches.remove("master");
        
        // If masterBranch was null than we know it was not in the list
        request.setAttribute("branchesHasMaster", masterBranch != null);
        request.setAttribute("branches", branches);
        request.setAttribute("branchesLength", branches.size());

        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }
}
