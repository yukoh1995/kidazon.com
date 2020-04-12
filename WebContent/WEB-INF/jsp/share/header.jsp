<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/kidazon.com/style.css">
  </head>
  <body>
    <header>
      <ul>
        <li class="line left"><h4>オンラインショッピングサイト</h4></li>
        <li class="line right"><h4>${ today }</h4></li>
        <c:choose>
          <c:when test="${ yourNo != null }">
            <li class="line right"><h4>${ yourName }さん</h4></li>
          </c:when>
          <c:when test="${ yourNo == null }">
            <li class="line right"><h4>「ゲスト」さん</h4></li>
          </c:when>
        </c:choose>
      </ul>
    </header>
    <hr>
  </body>
</html>