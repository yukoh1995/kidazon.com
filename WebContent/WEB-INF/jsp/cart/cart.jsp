<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>お買い物かご</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>●商品一覧</h3>
    <span style="color: red;"><c:out value="${ notSelect }" /></span>
    <c:forEach var="error" items="${ buyCntValiError }">
      <span style="color: red;"><c:out value="${ error }" /></span>
    </c:forEach>
    <c:forEach var="error" items="${ orderValiError }">
      <span style="color: red;"><c:out value="${ error }" /></span>
    </c:forEach>
      <table border="1">
        <tr>
          <th>選択</th>
          <th>商品コード</th>
          <th>商品名</th>
          <th>販売元</th>
          <th>価格</th>
          <th>購入数</th>
        </tr>
        <c:forEach var="product" items="${ inCartMap }">
          <tr>
            <td><input type="checkbox" name="inCartCheck" value="${ product.value.productCode }" form="reset"></td>
            <td>${ product.value.productCode }</td>
            <td>${ product.value.productName }</td>
            <td>${ product.value.maker }</td>
            <td><fmt:formatNumber value="${ product.value.unitPrice }" pattern="￥###,###" /></td>
            <td><input type="number" form="order" name="${ product.value.productCode }" value="${ product.value.buyCnt }"></td>
            <input type="hidden" name="productCode" value="${ product.value.productCode }" form="order">
          </tr>
        </c:forEach>
      </table>
      <c:if test="${ not empty inCartMap }">
        <form action="reset.html" id="reset">
          <input type="submit" value="取り消し" form="reset" >
        </form>
        <form action="resetAll.html">
          <input type="submit" value="買い物をやめる">
        </form>
        <form action="confCart.html" id="order">
          <input type="hidden" name="nothing" value="${ inCartMap }">
          <input type="submit" value="注文する" form="order">
        </form>
      </c:if>
      <button type="button" onclick="location.href='../userMenu.html'">メニューへ</button>
  </body>
</html>