<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% // logout
// 1. session 제거
session.invalidate();

// 2. 이동 : get
response.sendRedirect("../index.jsp");
%>