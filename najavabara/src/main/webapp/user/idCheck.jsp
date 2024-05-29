<%@page import="dto.UserDTO"%>
<%@page import="dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// idcheck
String id = request.getParameter("id");
UserDTO dto = new UserDTO();
dto.setId(id);
%>
<% // id db
UserDAO dao = new UserDAO();
dto = dao.getUser(dto);
%>
<%
int rs = 0;
if(dto != null){
	rs = 1;	
}
%>
{"rs":"<%=rs %>"}