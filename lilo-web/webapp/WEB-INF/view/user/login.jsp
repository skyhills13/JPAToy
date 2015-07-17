<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>LILO - Login</title>
    <link rel="stylesheet" href="/stylesheet/common.css"/>
    <link rel="stylesheet" href="/stylesheet/reset.css"/>
</head>
<body>
<div id="container">
    <div id="inner-container">
        <h1>로그인</h1>
        <c:if test="${not empty errorMessage}">
            <div class="errorMessage">
                    ${errorMessage}
            </div>
        </c:if>
        <form action="/user/login" method="post">
            <input type="email" name="id" placeholder="아이디"/>
            <input type="password" name="password" placeholder="비밀번호"/>
            <input type="submit" content="로그인"/>
        </form>
        <a href="/user/register">회원가입</a>
    </div>
</div>
</body>
</html>
