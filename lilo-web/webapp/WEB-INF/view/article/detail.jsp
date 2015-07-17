<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>LILO - Article - DetailView </title>
    <link rel="stylesheet" href="/stylesheet/detail.css"/>
    <link rel="stylesheet" href="/stylesheet/common.css"/>
    <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<div id="container">
    <div id="inner-container">
        <h1>글상세</h1>
        <c:if test="{not empty errorMessage}">
            <div class="errorMessage">
                    ${errorMessage}
            </div>
        </c:if>

        <div class="article">
            <div class="info">
                <p>
                    <span>글쓴이 :</span>
                    ${article.author.name}
                </p>

                <p>
                    <span>날짜 :</span>
                    ${article.createdTime}
                </p>

                <p></p>
            </div>
            <div class="spliter"></div>
            <div class="title">
                <span>제목 : </span>
                ${article.title}
            </div>

            <div class="spliter"></div>
            <div class="content">
                ${article.content}
            </div>

            <div class="spliter"></div>
            <div class="comment">
                <div>
                    <strong>
                        <c:choose>
                            <c:when test="${not empty article.comments}">${fn:length(article.comments)} </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose>
                    </strong> Comments
                </div>

                <c:forEach var="comment" items="${article.comments}">
                    <div class="piece">
                        <p class="author_name">${comment.author.name}</p>
                        <p class="time">${comment.createdTime}</p>
                        <p class="content">> ${comment.content}</p>
                    </div>
                </c:forEach>
                <!-- Comment Input -->
                <div id="input_container">
                    <div id="sub_container">
                        <form method="post" action="/article/comment">
                            <input type="hidden" name="articleId" value="${article.id}">
                            <textarea name="content" placeholder="댓글"></textarea>
                            <input type="submit" value="Submit">
                        </form>
                    </div>
                    <div class="hamburger">
                    </div>
                </div>
            </div>

            <div class="control_panel">
                <a href="/article/list">글목록</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>

