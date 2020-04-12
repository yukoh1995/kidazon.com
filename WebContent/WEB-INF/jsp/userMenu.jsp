<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/kidazon.com/style.css">
    <title>ユーザーメニュー!</title>
  </head>
  <body>
    <jsp:include page="share/header.jsp" />
    <div class="center">
      <table>
        <c:if test="${ yourNo == null }">
        <tr>
          <td><a href="register/inputMemInfo.html">新規会員登録</a></td>
          <td><p>会員情報の登録を行います。</p></td>
        </tr>
        </c:if>
        <tr>
          <td><a href="modify/memInfo.html">会員情報変更・削除</a></td>
          <td><p>会員情報の変更、削除を行います。</p></td>
        </tr>
        <tr>
          <td><a href="search/searchProduct.html">商品検索</a></td>
          <td><p>購入する商品の検索を行います。</p></td>
        </tr>
        <tr>
          <td><a href="cart/cart.html">お買い物かご</a></td>
          <td><p>商品の注文を行います。</p></td>
        </tr>
      </table>
      <c:choose>
        <c:when test="${ yourNo == null }">
          <button type="button" onclick="location.href='userLogin.html'">ログイン</button>
        </c:when>
        <c:when test="${ yourNo != null }">
          <button type="button" onclick="location.href='logout.html'">ログアウト</button>
        </c:when>
      </c:choose>
    </div>
  </body>
</html>