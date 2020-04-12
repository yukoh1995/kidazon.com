<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会員情報修正結果</title>
  </head>
  <body>
    <jsp:include page="../share/header.jsp" />
    <h3>会員情報の変更が完了しました。</h3>
    <button type="button" onclick="location.href='../userMenu.html'">メニューへ</button>
  </body>
</html>