<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>お買い物確認</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>●商品一覧</h3>
    <span style="color: red;"><c:out value="${ cantBuy }" /></span>
    <form action="orderResult.html">
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
      <h3>●料金</h3>
      <table border="1">
        <tr>
          <th>小計</th>
          <td><fmt:formatNumber value="${ totalPrice }" pattern="￥###,###" /></td>
        </tr>
        <tr>
          <th>消費税</th>
          <td><fmt:formatNumber value="${ displayTax }" pattern="￥###,###" /></td>
        </tr>
        <tr>
          <th>合計金額</th>
          <td><fmt:formatNumber value="${totalPrice + displayTax }" pattern="￥###,###" /></td>
        </tr>
      </table>
      <button type="button" onclick="location.href='resetAll.html'">買い物をやめる</button>
      <input type="submit" value="注文する">
      <button type="button" onclick="location.href='cart.html'">戻る</button>
    </form>
  </body>
</html>