<%-- 
    Document   : index
    Created on : 09-Sep-2014, 23:31:18
    Author     : mat
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                    <td><c:out value="${commits.date}" /></td>
                    <td><c:out value="${commits.message}" /></td>
                    <td><c:out value="${commits.userCommited}" /></td>
                </tr>
            </c:forEach>
       </table>
    </body>
</html>
