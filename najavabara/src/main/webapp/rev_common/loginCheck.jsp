<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("로그인 체크");
	System.out.println(session.getAttribute("id"));
	if(session.getAttribute("id") == null){
		response.sendRedirect("../login.jsp");
	}
%>