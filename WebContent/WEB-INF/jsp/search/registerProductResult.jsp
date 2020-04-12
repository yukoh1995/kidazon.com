<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商品登録結果</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>以下の商品をお買い物かごに登録しました。</h3>
    <h3>●商品一覧</h3>
    <table border="1">
      <tr>
        <th>商品コード</th>
        <th>商品名</th>
        <th>販売元</th>
        <th>価格</th>
        <th>購入数</th>
      </tr>
      <c:forEach var="product" items="${ inCartMap }">
        <tr>
          <td>${ product.value.productCode }</td>
          <td>${ product.value.productName }</td>
          <td>${ product.value.maker }</td>
          <td><fmt:formatNumber value="${ product.value.unitPrice }" pattern="￥###,###" /></td>
          <td>${ product.value.buyCnt }</td>
        </tr>
      </c:forEach>
    </table>
      <button type="button" onclick="location.href='../cart/cart.html'">お買いものかご</button>
      <button type="button" onclick="location.href='searchProduct.html'">戻る</button>
  </body>
</html>