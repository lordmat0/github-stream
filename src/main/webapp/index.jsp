<%-- 
    Document   : newjsp
    Created on : 02-Sep-2014, 20:14:52
    Author     : mat
--%>

<%@page import="com.lordmat.githubstream.page.PageManager"%>
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
        <p>Page created at:
            <%
                out.write(new SimpleDateFormat("HH:mm:ss:SSS yyyy-MM-dd").format(new Date()));
            %>
        </p>
        <div>
            Time now: 
            <div id="time" style="display: inline-block"></div> 
            <script type="text/javascript">setInterval(function() {
                    document.getElementById("time").innerHTML = new Date();
                }, 1000)
            </script>
        </div>
    <p>

        <%
            out.write("<h1>GitHubData Live</h1>");

            out.write(PageManager.makePage());

            out.write("<h2> Last Entry</h2><p>");
            out.write(StartManager.gitHubAPI.getCommits().lastEntry().toString());

        //out.write("<p>" + GitHubAPI.jsonObject.get("feeds_url") + "</p>");
%>
    </p>

</body>
</html>
