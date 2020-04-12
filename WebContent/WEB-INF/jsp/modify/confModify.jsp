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
    <h3>この内容に変更しますか？</h3>
    <h3>●会員情報</h3>
    <form action="modifyResult.html">
      <table border="1">
        <tr>
          <th>会員NO</th>
          <td>${ userInfo.memberNo }</td>
        </tr>
        <tr>
        <tr>
          <th>氏名</th>
          <td>${ modifyMemInfoFormModel.name }</td>
        </tr>
        <tr>
          <th>年齢</th>
          <td>${ modifyMemInfoFormModel.age }</td>
        </tr>
        <tr>
          <th>性別</th>
          <td>
            <c:choose>
              <c:when test="${ modifyMemInfoFormModel.sex == 'm' }">
                <p>男性</p>
              </c:when> 
              <c:when test="${ modifyMemInfoFormModel.sex == 'f' }">
                <p>女性</p>
              </c:when>
            </c:choose>
          </td>
        </tr>
        <tr>
          <th>郵便番号</th>
          <td>${ modifyMemInfoFormModel.zip }</td>
        </tr>
        <tr>
          <th>住所</th>
          <td>${ modifyMemInfoFormModel.addr }</td>
        </tr>
        <tr>
          <th>電話番号</th>
          <td>${ modifyMemInfoFormModel.phone }</td>
        </tr>
        <tr>
          <th>登録日</th>
          <td>${ userInfo.registerDate }</td>
        </tr>
      </table>
      <input type="submit" value="実行">
      <button type="button" onclick="location.href='modifyMemInfo.html'">戻る</button>
    </form>

  </body>
</html>