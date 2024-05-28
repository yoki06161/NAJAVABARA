<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 현재 섹션에 저장된 값을 지운다.
session.invalidate();
response.sendRedirect("index.jsp");
%>