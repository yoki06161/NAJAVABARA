<%@page import="dto.HobbyBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
int totalCount = (int)request.getAttribute("totalCount");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>astronomical.jsp</title>
</head>
<style>
   	#wrap {
  		max-width: 1200px;
  		margin: 0 auto;
  	}
</style>
<body style="margin:0 auto;">
	<div id="wrap">
		<%@ include file="../common/menu.jsp" %>
		<%@ include file="hobbymenu.jsp" %>
		<%@ include file="commonsearch.jsp" %>
		<b>전체 : <%=totalCount%></b>
		<%@include file="commontable.jsp" %>
	</div>
</body>
</html>