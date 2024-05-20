<%@page import="dao.UserDAO"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
UserDAO dao = new UserDAO();
//UserDTO dto = new UserDTO();
UserDTO dto = (UserDTO) request.getAttribute("user");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<script>
	function del(){
		 var pw = document.getElementById("pw").value;
         if (pw=="") {
             alert("비밀번호를 입력해주세요.");
             return;
         }
		const input = confirm("정말 탈퇴하시겠습니까?");
		if(input){
			 document.getElementById("delete").submit();
			 
		}else{
			alert('탈퇴를 취소 했습니다.');
		}
	}
</script>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>회원탈퇴</h1>
	<br>
	<form action="deleteProc.usr" method="post" id="delete">
		<table>
			<tr>
				<td colspan="3"><h4>아이디 <%=session.getAttribute("id") %>
					의 회원 탈퇴를 계속 진행하시려면 비밀번호를 입력해주세요</h4></td>
			</tr>
			<tr>
				<td><h5>비밀번호:</h5></td>
				<td><input type="password" name="pw" id="pw" class="form-control"
					placeholder="비밀번호" required></td>
				<td><input type="button" onclick="del()" value="회원 탈퇴" class="btn btn-primary">
				</td>
			</tr>
		</table>
	</form>
	<a href="../user/update.usr" class="btn btn-outline-primary">내 정보 수정</a>
</body>
</html>