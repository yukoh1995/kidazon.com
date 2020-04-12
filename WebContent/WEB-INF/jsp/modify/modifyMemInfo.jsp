<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会員情報</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>会員情報を入力してください。</h3>
    <h3>●会員情報</h3>
    <form action="confModify.html" method="post">
      <table border="1">
        <tr>
          <th>会員NO</th>
          <td>${ userInfo.memberNo }</td>
        </tr>
        <tr>
          <th>氏名</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.name == null }">
                <input type="text" name="name" value="${ userInfo.name }"> 
              </c:when>
              <c:otherwise>
                <input type="text" name="name" value="${ modifyMemInfoFormModel.name }"> 
              </c:otherwise>
            </c:choose>
            <c:forEach var="error" items="${ nameValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
          
        </tr>
        <tr>
          <th>パスワード</th>
          <td>
            <input type="password" name="pass" value="${ tryPass }">
            <c:forEach var="error" items="${ passValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
            <span class="error"><c:out value="${ samePass }" /></span>
          </td>
        </tr>
        <tr>
        <tr>
          <th>パスワード(確認用)</th>
          <td>
            <input type="password" name="passConf" value="${ tryPassConf }">
            <c:forEach var="error" items="${ passConfValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
        <tr>
          <th>年齢</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.age == null }">
                <input type="text" name="age" value="${ userInfo.age }"> 
              </c:when>
              <c:otherwise>
                <input type="text" name="age" value="${ modifyMemInfoFormModel.age }"> 
              </c:otherwise>
            </c:choose>
            <c:forEach var="error" items="${ ageValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>性別</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.sex == null }">
                <select name="sex">
                  <option value="m" <c:if test="${ userInfo.sex == 'M'}">selected</c:if>>男性</option>
                  <option value="f" <c:if test="${ userInfo.sex == 'F'}">selected</c:if>>女性</option>
                </select>
              </c:when>
              <c:otherwise>
                <select name="sex">
                  <option value="m" <c:if test="${ model.sex == 'm'}">selected</c:if>>男性</option>
                  <option value="f" <c:if test="${ model.sex == 'f'}">selected</c:if>>女性</option>
                </select>
              </c:otherwise>
            </c:choose>
            <c:forEach var="error" items="${ sexValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>郵便番号</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.zip == null }">
                <input type="text" name="zip" value="${ userInfo.zip }"> 
              </c:when>
              <c:otherwise>
                <input type="text" name="zip" value="${ modifyMemInfoFormModel.zip }"> 
              </c:otherwise>
            </c:choose>
            <c:forEach var="error" items="${ zipValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>住所</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.addr == null }">
                <textarea name="addr">${ userInfo.addr }</textarea>
              </c:when>
              <c:otherwise>
                <textarea name="addr">${ modifyMemInfoFormModel.addr }</textarea>
              </c:otherwise>
            </c:choose>
            <c:forEach var="error" items="${ addrValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>電話番号</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.phone == null }">
                <input type="text" name="phone" value="${ userInfo.tel }"> 
              </c:when>
              <c:otherwise>
                <input type="text" name="phone" value="${ modifyMemInfoFormModel.phone }"> 
              </c:otherwise>
            </c:choose>
            <c:forEach var="error" items="${ phoneValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>登録日</th>
          <td><fmt:formatDate value="${ userInfo.registerDate }" pattern="yyyy/MM/dd" /></td>
        </tr>
      </table>
      <input type="submit" value="確認">
      <button type="button" onclick="location.href='memInfo.html'">戻る</button>
      <button type="button" onclick="location.href='resetMemInfo.html'">クリア</button>
    </form>
  </body>
</html> 