<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
<form method="post" action="joinAction.jsp">
	<table>
		<!-- id중복값 처리 안함. id중복되면 오류뜸 -->
		<tr>
		<!-- name = "외부로 내보낼 변수명". name을 안쓰면 서버로 변수를 못내보낸다. 여기서 name으로 이름지은 id가 request.getParameter("id")에서 id로 불린다. -->
		<td>아이디</td>
		<td><input type="text" name = "id" class = "text_id" placeholder="아이디를 입력하세요" maxlength="20px" required="required"></td>
		</tr>
		<tr>
		<td>비밀번호</td>
		<td><input type="password" name = "password" class = "text_pw" placeholder="비밀번호를 입력하세요" maxlength="20px" required="required"></td>
		</tr>
		<tr>
		<td>비밀번호 재입력</td>
		<td><input type="password" name = "password_re" class = "text_pw_re" placeholder="비밀번호를 입력하세요" maxlength="20px"></td>
		</tr>
		<tr>
		<td>이름
		</td>
		<td><input type="text" name = "name" class = "text_name" placeholder="이름을 입력하세요" maxlength="20px" required="required"></td>
		</tr>
		<tr>
		<td>지역선택</td>
		<!-- 이해 못함. 나중에 수정할것 -->
		<td>
		<select name = "area" class = "text_area">
		<option>서울 특별시</option>
		<option>부산 광역시</option>
		<option>대구 광역시</option>
		<option>인천 광역시</option>
		<option>광주 광역시</option>
		<option>대전 광역시</option>
		<option>울산 광역시</option>
		<option>세종특별자치도</option>
		<option>경기도</option>
		<option>강원특별자치도</option>
		<option>충청북도</option>
		<option>충청남도</option>
		<option>전북특별자치도</option>
		<option>전라남도</option>
		<option>경상북도</option>
		<option>경상남도</option>
		<option>제주특별자치도</option>
		</select>
		</td>
		</tr>
	</table>
		<input type="submit" class="s_btn" value="회원가입하기">
		<%
		// em의 값이 널일시 아무것도 출력않게 함. 뒤로가거나, bo는 트루일시의 값
		if(session.getAttribute("em") == null) {%>
		<%}else{%>
		<label><%= session.getAttribute("em") %></label>
		<%session.removeAttribute("em"); %>
		<% }%>
		
	</form>
</body>
</html>