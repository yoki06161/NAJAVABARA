<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// sessionCheck.jsp
if(session.getAttribute("id") == null){
	response.sendRedirect("../user/login.jsp");
}
%> 