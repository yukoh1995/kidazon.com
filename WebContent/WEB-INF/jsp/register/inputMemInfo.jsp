<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会員情報入力</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>会員情報を入力してください。</h3>
    <h3>●会員情報</h3>
    <form action="confMemInfo.html" method="post">
      <table border="1">
        <tr>
          <th>氏名</th>
          <td>
            <input type="text" name="name" value=${ model.name }>
            <c:forEach var="error" items="${ nameValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
           </td>
        </tr>
        <tr>
          <th>パスワード</th>
          <td>
            <input type="password" name="pass" value=${ model.pass }>
            <c:forEach var="error" items="${ passValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>パスワード(確認用)</th>
          <td>
            <input type="password" name="passConf" value=${ model.passConf }>
            <c:forEach var="error" items="${ passConfValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>年齢</th>
          <td>
            <input type="text" name="age" value=${ model.age }>
            <c:forEach var="error" items="${ageValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>性別</th>
          <td>
            <select name="sex">
              <option value="m" <c:if test="${ model.sex == 'm'}">selected</c:if>>男性</option>
              <option value="f" <c:if test="${ model.sex == 'f'}">selected</c:if>>女性</option>
            </select>
            <c:forEach var="error" items="${ sexValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>郵便番号</th>
          <td>
            <input type="text" name="zip" value="${ model.zip }">
            <c:forEach var="error" items="${ zipValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>住所</th>
          <td>
            <textarea name="addr">${ model.addr }</textarea>
            <c:forEach var="error" items="${ addrValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
        <tr>
          <th>電話番号</th>
          <td>
            <input type="text" name="phone" value=${ model.phone }>
            <c:forEach var="error" items="${ phoneValiError }">
              <span style="color: red;"><c:out value="${ error }" /></span>
            </c:forEach>
          </td>
        </tr>
      </table>
      <input type="submit" value="確認">
      <button type="button" onclick="location.href='../userMenu.html'">戻る</button>
      <button type="button" onclick="location.href='inputMemInfo.html'">クリア</button>
    </form>
  </body>
</html>