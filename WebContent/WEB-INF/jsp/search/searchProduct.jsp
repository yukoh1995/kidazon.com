<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/kidazon.com/style.css">
    <title>商品検索</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>検索条件を入力してください。</h3>
    <form action="searchingProduct.html" method="post" onsubmit="scrollToTop()">
      <table border="1">
        <tr>
          <th>カテゴリ</th>
          <td>
            <select name="category">
              <option value="">全て</option>
              <c:forEach var="category" items="${ categoryList }">
                <option value="${ category.ctgrId }" <c:if test="${ prodSearchCondition.category == category.ctgrId }">selected</c:if> >${ category.name } </option>
              </c:forEach>
            </select>
          </td>
        </tr>
        <tr>
          <th>商品名</th>
          <td><input type="text" name="name" value="${ prodSearchCondition.name }"></td>
        </tr>
        <tr>
          <th>販売元</th>
          <td><input type="text" name="maker" value="${ prodSearchCondition.maker }"></td>
        </tr>
        <tr>
          <th>金額上限</th>
          <td>
            <input type="text" name="higherPrice" value="${ prodSearchCondition.higherPrice }">
            <c:forEach var="error" items="${ highPriceValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
            <c:forEach var="error" items="${ doublePriceValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>金額下限</th>
          <td>
            <input type="text" name="lowerPrice" value="${ prodSearchCondition.lowerPrice }">
            <c:forEach var="error" items="${ lowPriceValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
      </table>
      <input type="submit" value="検索">
      <input type="reset" value="クリア">
    </form>
    <form action="userMenu.html">
        <input type="submit" value="戻る">
    </form>
    <h3>●商品一覧</h3>
      <p><c:out value="${ data0 }" /></p>
      <c:if test="${ not empty productMap }">
        <div class="paging">
          <form action="top.html">
            <input class="pageButton" type="submit" value="＜＜" />
          </form>
          <form action="return.html">
            <input type="hidden" name="returnPage" value="${returnPage}" /> 
            <input class="pageButton" type="submit" value="＜" />
          </form>
          <c:out value="${ thisPage }/${ lastPageNum }" />
          <form action="next.html">
            <input type="hidden" name="nextPage" value="${nextPage}">
            <input class="pageButton" type="submit" value="＞">
          </form>
          <form action="last.html">
            <input class="pageButton" type="submit" value="＞＞">
          </form>
        </div>
        <form action="registerProductResult.html">
          <span class="error"><c:out value="${ buyCntError }" /></span>
          <span class="error"><c:out value="${ checkboxIsBlank }" /></span>
          <c:forEach var="error" items="${ stockErrorList }">
            <span style="color: red;"><c:out value="${ error }" /></span>
          </c:forEach>
          <c:forEach var="error" items="${ inCartValiError }">
            <span style="color: red;"><c:out value="${ error }" /></span>
          </c:forEach>
          <table border="1">
            <tr>
              <th>選択</th>
              <th>商品コード</th>
              <th>商品名</th>
              <th>販売元</th>
              <th>金額(単価)</th>
              <th>メモ</th>
              <th>購入数</th>
            </tr>
            <c:forEach var="product" items="${ productMap }">
              <tr>
                <td>
                  <input id="checkbox" type="checkbox" name="buyItem" value="${ product.value.productCode }">
                </td>
                <td>${ product.value.productCode }</td>
                <td><a href="productDetail.html?code=${ product.value.productCode }">${ product.value.productName }</a></td>
                <td>${ product.value.maker }</td>
                <td><fmt:formatNumber value="${ product.value.unitPrice }" pattern="￥###,###" /></td>
                <td>${ product.value.memo }</td>
                <td>
                  <input id="buyCnt" type="number" name="${ product.value.productCode }">
                </td>
              </tr>
            </c:forEach>
          </table>
          <input type="hidden" name="detail" value="false">
          <input class="submitBtn" type="submit" value="お買い物かごに入れる">
        </form>
      </c:if>
    <script>
    </script>
  </body>
</html>