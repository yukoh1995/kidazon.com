<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会員情報確認</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>この内容で登録しますか？</h3>
    <h3>●会員情報</h3>
    <form action="outputMemnum.html" method="post">
      <table border="1">
        <tr>
          <th>氏名</th>
          <td>${ memInfoForm.name }</td>
        </tr>
        <tr>
          <th>年齢</th>
          <td>${ memInfoForm.age }</td>
        </tr>
        <tr>
          <th>性別</th>
          <td>
            <c:choose>
              <c:when test="${ memInfoForm.sex == 'm' }">
                <p>男性</p>
              </c:when> 
              <c:when test="${ memInfoForm.sex == 'f' }">
                <p>女性</p>
              </c:when>
            </c:choose>
          </td>
        </tr>
        <tr>
          <th>郵便番号</th>
          <td>${ memInfoForm.zip }</td>
        </tr>
        <tr>
          <th>住所</th>
          <td>${ memInfoForm.addr }</td>
        </tr>
        <tr>
          <th>電話番号</th>
          <td>${ memInfoForm.phone }</td>
        </tr>
      </table>
      <input type="submit" name="登録">
      <button type="button" onclick="location.href='inputMemInfo.html'">戻る</button>
    </form>

  </body>
</html>