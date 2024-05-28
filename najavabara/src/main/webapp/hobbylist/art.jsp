<%@page import="proj.dto.HBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
int totalCount = (int)request.getAttribute("totalCount");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>art.jsp</title>
</head>
<style>
   	#wrap {
  		max-width: 1200px;
  		margin: 0 auto;
  	}
</style>
<body>
	<div id="wrap">
		<%@ include file="../common/menu.jsp" %>
		<%@ include file="commonsearch.jsp" %>
		<b>전체 : <%=totalCount%></b>
		<%@include file="commontable.jsp" %>
	</div>
</body>
</html>