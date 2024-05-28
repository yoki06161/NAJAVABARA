<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="../css/common.css">
<%
request.setCharacterEncoding("UTF-8");
String id2 = (String)session.getAttribute("id");
if(id2 == null){%>
<a href="user/join.jsp">회원가입</a>
<%}%>