<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商品詳細</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>●商品詳細</h3>
    <c:forEach var="error" items="${ inCartValiError }">
      <span style="color: red;"><c:out value="${ error }" /></span>
    </c:forEach>
    <c:forEach var="error" items="${ stockErrorList }">
      <span style="color: red;"><c:out value="${ error }" /></span>
    </c:forEach>
    <form action="registerProductResult.html">
      <table border="1">
        <tr>
          <th>商品名</th>
          <td>${ thisProduct.productName }</td>
        </tr>
         <tr>
          <th>画像</th>
          <td><img src="img/${ thisProduct.productName }"></td>
        </tr>
         <tr>
          <th>商品説明</th>
          <td>${ thisProduct.memo }</td>
        </tr>
         <tr>
          <th>価格</th>
          <td><fmt:formatNumber value="${ thisProduct.unitPrice }" pattern="￥###,###" /></td>
        </tr>
         <tr>
          <th>購入数</th>
          <td><input type="text" name="${ thisProduct.productCode }" value="${ thisProduct.buyCnt }">個</td>
        </tr>
      </table>
      <input type="hidden" name="buyItem" value="${ thisProduct.productCode }">
      <input type="hidden" name="detail" value="true">
      <input type="submit" value="お買いものかごにいれる">
      <button type="button" onclick="location.href='searchProduct.html'">戻る</button>
    </form>
  </body>
</html>