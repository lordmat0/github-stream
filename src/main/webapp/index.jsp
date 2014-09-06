<%-- 
    Document   : newjsp
    Created on : 02-Sep-2014, 20:14:52
    Author     : mat
--%>

<%@page import="com.lordmat.githubstream.web.StartManager"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.lordmat.githubstream.api.GitHubAPI"%>
<%@page import="com.lordmat.githubstream.api.GitHubCommit"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>Current date/time:
        <%
        out.write(new SimpleDateFormat("HH:mm:ss").format(new Date()));    
        %>
        </p>
        <p>
            
            <%
            
            out.write("<h1>JSON Array</h1>");
            
            
            for(GitHubCommit commit : StartManager.gitHubAPI.getCommits().values()){
                out.write(commit.toString());
                out.write("<p>");
            }
            
            //out.write("<p>" + GitHubAPI.jsonObject.get("feeds_url") + "</p>");
        %>
        </p>
        
    </body>
</html>
