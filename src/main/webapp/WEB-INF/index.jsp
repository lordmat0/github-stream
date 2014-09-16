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
        <link href="../res/css/bootstrap/bootstrap.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>

        <div class="container">
            <h1>Commits for project</h1>

            <div class="alert alert-dismissable alert-success container-fluid row" >
              
                    <img class="col-sm-1 col-md-1 col-lg-1" alt="dunno" src="https://avatars3.githubusercontent.com/u/4976353?v=2&s=460" style="max-width: 150px;" >
                    
                    <div class="col-md-8" > 
                        <div class="row">
                            <div class="col-md-3 col-sm-3">Matthew</div>
                            <div class="col-md-7 col-sm-7">2d233f8bb0981d0116b9537b8e44000733cb6958</div>
                            <div class="col-md-2 col-sm-2"> 2014-09-15T23:57:50Z</div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-sm-12">
                                Fixed issue where resource was not found due to be being case senistive. 
                                (On OSX, need to test on other operating systems)
                            </div>
                        </div>
                    </div>
               
            </div>

        </div>







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
