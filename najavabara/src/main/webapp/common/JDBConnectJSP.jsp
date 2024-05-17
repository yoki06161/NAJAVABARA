<%@page import="java.sql.Connection"%>
<%@page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DB 연동</title>
</head>
<body>
<h2>JDBC 연동</h2>
<%
Connection conn = JDBConnect.getConnection();    
//conn.close();
%>
</body>
</html>