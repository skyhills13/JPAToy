<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>LILO - Article</title>
    <link rel="stylesheet" href="/stylesheet/list.css"/>
    <link rel="stylesheet" href="/stylesheet/common.css"/>
    <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<div id="container">
    <div id="inner-container">
        <h1>글목록</h1>
        <c:if test="{not empty errorMessage}">
            <div class="errorMessage">
                ${errorMessage}
            </div>
        </c:if>
        <ol class="board">
            <a href="/article/register">새글쓰기</a>
            <c:forEach var="article" items="${articles}">
                <li>
                    <a href="/article/${article.id}">
                    <div class="row">
                        <p class="title">> ${article.title}</p>
                        <p class="time">${article.createdTime}</p>
                    </div>
                    </a>
                </li>
            </c:forEach>
        </ol>
    </div>
</div>
</body>
</html>
