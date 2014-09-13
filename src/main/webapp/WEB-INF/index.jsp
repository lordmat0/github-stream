<%-- 
    Document   : index
    Created on : 09-Sep-2014, 23:31:18
    Author     : mat
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- jQuery -->
        <script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
        
        <!-- Bootstrap -->
        <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        
        <script type="text/javascript" src="../githubstream/res/js/main.js"></script>
        
        <link rel="stylesheet" href="../githubstream/res/css/bootstrap/bootstrap.css">
        
    </head>
    <body>
        <h1>Hello World! in custom index.jsp page</h1>
        
        <table>
            <tr>
                <th>ID</th>
                <th>Date</th>

                <th>Message</th>
                <th>User</th>
            </tr>
            <c:forEach items="${commits}" var="commits">
                <tr>
                    <td>${commits.id}</td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" value="${commits.date}" /></td>
                    <td><c:out value="${commits.message}" /></td>
                    <td><c:out value="${commits.userCommited}" /></td>
                </tr>
            </c:forEach>
       </table>
    </body>
</html>
