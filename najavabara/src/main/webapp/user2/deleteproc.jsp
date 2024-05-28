<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteproc.jsp</title>
</head>
<body>
	<%@ include file="dbconn.jsp" %>
	<%
		request.setCharacterEncoding("UTF-8");
		
		// 세션에서 현재 로그인한 사용자의 ID와 PW 값을 가져옴
	    String currentUserId = (String) session.getAttribute("id");
	    String currentUserPw = (String) session.getAttribute("pw");
	    
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		PreparedStatement pstmt = null;
		
		try{
            if (id.equals(currentUserId) && pw.equals(currentUserPw)) {
                String sql = "DELETE FROM user WHERE id = ? AND pw = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, pw);
                pstmt.executeUpdate();
    %>
                <script>
                    alert("계정이 삭제되었습니다.");
                	window.location.href = "../index.jsp";
                </script>
                <%
                	session.invalidate();
                %>
    <%
            } else {
    %>
                <script>
                    alert("로그인한 계정 정보와 일치하지 않습니다. 계정 삭제 실패.");
                    window.location.href = "delete.jsp";
                </script>
                    <%-- <%response.sendRedirect("delete.jsp"); %> --%>
    <%
            }
            
		}catch(SQLException e){
			out.println("SQLException : " + e.getMessage());
		}finally{
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	%>
	
</body>
</html>