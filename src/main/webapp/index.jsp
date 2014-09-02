<%-- 
    Document   : newjsp
    Created on : 02-Sep-2014, 20:14:52
    Author     : mat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            double num = Math.random();
            if (num > 0.80) {
        %>
        <h2>You'll have a luck day!</h2><p>(<%= num%>)</p>
        <%
        } else {
        %>
        <h2>Well, life goes on ... </h2><p>(<%= num%>)</p>
        <%
            }
            
            String s = "asd";
            out.write(s);
        %>
    </body>
</html>
