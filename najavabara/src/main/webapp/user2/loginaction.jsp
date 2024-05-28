<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="../css/common.css">
<%
request.setCharacterEncoding("UTF-8");
String id1 = (String)session.getAttribute("id");
String name1 = (String)session.getAttribute("name");
String str1 = "&quot;" + name1 + "&quot; 님 환영합니다!";
if(id1 != null){%>
<%=str1 %>
<a class="idcheck" href="user/update.jsp">내 정보 수정</a>
<!-- <a class="idcheck" href="user/idcheck.jsp">내 정보</a> -->
<a class="logout" href="user/delete.jsp">계정삭제</a>
<a class="logout" href="user/logout.jsp">로그아웃</a>
<%}else{%>
<a href="user/login.jsp">로그인</a>
<%}%>