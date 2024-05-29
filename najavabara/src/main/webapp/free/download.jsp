<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String saveDirectory = application.getRealPath("/Uploads");
String saveFilename = request.getParameter("sName");
String originalFilename = request.getParameter("oName");

File file = new File(saveDirectory, saveFilename);
InputStream inputStream = new FileInputStream(file);

response.reset();
response.setContentType("application/octet-stream");
response.setHeader("Content-Disposition", 
					"attatchment; filename=\"" + originalFilename + "\"");
response.setHeader("Content-Length", "" + file.length());

out.clear();

OutputStream outputStream = response.getOutputStream();

byte b[] = new byte[(int)file.length()];
int readBuffer = 0;
while((readBuffer = inputStream.read(b)) > 0) {
	outputStream.write(b, 0, readBuffer);
}


%>