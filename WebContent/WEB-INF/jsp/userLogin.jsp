<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/kidazon.com/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>ユーザーログイン</title>
  </head>
  <body>
    <jsp:include page="share/header.jsp" />
    <div  class="center">
      <a href="register/inputMemInfo.html">新規会員の方はこちら</a>
      <span class="error"><c:out value="${ failLogin }" /></span>
      <form action="doLogin.html" method="post">
        <table>
          <tr>
            <td>会員NO</td>
            <td>
              <input type="text" name="memNo" >
              <form:errors cssClass="check" path="loginFormModel.memNo" />
            </td>
          </tr>
          <tr>
            <td>パスワード</td>
            <td>
              <input type="password" name="pass">
              <form:errors cssClass="check" path="loginFormModel.pass" />
            </td>
          </tr>
        </table>
        <input type="submit" value="ログイン">
        <input type="reset" value="クリア">
      </form>
    </div>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  </body>
</html>