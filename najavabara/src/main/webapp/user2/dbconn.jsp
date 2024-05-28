<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>

<%
Connection conn = null;
String driver = "com.mysql.cj.jdbc.Driver";
String url = "jdbc:mysql://54.253.205.165:3306/teampro?serverTimezone=UTC";
String user = "hobby";
String pw = "hobby";

out.println("conn ok!!");
Class.forName(driver);
conn = DriverManager.getConnection(url, user, pw);
%>