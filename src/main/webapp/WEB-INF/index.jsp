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
        <title><c:out value="${branchName}"/> Commits</title>
        <!-- jQuery -->
        <script src="//code.jquery.com/jquery-2.1.1.min.js"></script>

        <!-- Bootstrap -->
        <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

        <script type="text/javascript" src="../githubstream/res/js/main.js"></script>

        <link rel="stylesheet" href="../githubstream/res/css/bootstrap/bootstrap.css">
        <link href="../res/css/bootstrap/bootstrap.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>

        <%-- Nav bar --%>
        <div class="navbar navbar-default">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#"><c:out value="${project}"/></a>
            </div>
            <div class="navbar-collapse collapse navbar-responsive-collapse">
                <ul class="nav navbar-nav">
                    <c:if test="${branchesLength == 0}">
                        <li>
                            <a href="#">No Branches found :(</a>
                        </li>
                    </c:if>
                    <c:forEach items="${branches}" var="branches">        
                        <li>
                            <a href="?branch=<c:out value='${branches.key}' />"><c:out value="${branches.key}"/></a>
                        </li>
                    </c:forEach>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#" data-toggle="modal" data-target="#modal-about">About</a></li>
                </ul>
            </div>
        </div>


        <div class="container">

            <c:if test="${commitsLength == 0}">
                <h3>No commits found - try refreshing the page</h3>
                <a href="<c:out value='${projectUrl}'/>" target="_blank"> Link to project on GitHub </a>
            </c:if>

            <section>
                <c:forEach items="${commits}" var="commits">
                    <article class="commit alert alert-dismissable alert-info container-fluid row" >

                        <img class="commit-user-avatar col-sm-1 col-md-1 col-lg-1" alt="Missing Avatar" src="<c:out value='${commits.userCommited.avatarUrl}' />" style="max-width: 150px;" >

                        <div class="col-md-8" > 
                            <div class="row">

                                <div class="col-md-3 col-sm-3">
                                    <strong>
                                        <a class="commit-accounturl" href="<c:out value='${commits.userCommited.accountUrl}' />" target="_blank" >
                                            <c:out value="${commits.userCommited.userName}" />
                                        </a>
                                    </strong>
                                </div>

                                <div class="col-md-7 col-sm-7">
                                    <strong>
                                        <a class="commit-id" href="<c:out value='${commits.idUrl}' />" target="_blank">
                                            <c:out value="${commits.id}" />
                                        </a>
                                    </strong>
                                </div>

                                <div class="commit-date col-md-2 col-sm-2">
                                    <strong><fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" value="${commits.date}"/></strong>
                                </div>

                            </div>

                            <div class="commit-message row">
                                <div class="col-md-12 col-sm-12"><c:out value="${commits.message}" /></div>
                            </div>
                        </div>

                    </article>
                </c:forEach>

            </section>

            <img id="img-loading" class="center-block" src="res/img/loader.gif">

        </div>

        <div class="modal fade" id="modal-about">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;</button>
                        <h4 class="modal-title">GitHubStream</h4>
                    </div>
                    <div class="modal-body">
                        <p>
                            This project was just a bit of fun to learn Java web services.
                        </p>
                        <p>
                            A link to the project source code can be found 
                            <a href="https://github.com/lordmat0/github-stream" target="_blank">here</a>
                        </p>
                    </div>
                    <div class="modal-footer">
                        <a href="#" class="btn btn-default" data-dismiss="modal">Close</a>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>

    </body>
</html>
