<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
function idCheck(){
	const id = $('#id').val();
    if (id=="") {
        // 아이디가 빈 칸인 경우 처리
        $('#isIdTrue').hide();
    	$('#isIdFalse').hide();
        $('#idLengthMessage').text('아이디를 입력하세요.');
        return; // 빈 칸일 경우 AJAX 요청을 보내지 않음
    }
	
	//const id = document.querySelector('#id').value;
	//console.log(id);
	const param = {id:id};
	
	// 아이디 글자 수 체크
    const idLength = id.length;
    const minIdLength = 3; // 최소 글자 수
	
    if (idLength < minIdLength) {
    	$('#isIdTrue').hide();
    	$('#isIdFalse').hide();
        $('#idLengthMessage').text('최소 ' + minIdLength + '글자 이상 입력해야 합니다.');
        return; // 최소 글자 수 미만이면 AJAX 요청을 보내지 않음
    } else {
        $('#idLengthMessage').text('');
    
	    // AJAX 요청 보내기
		$.ajax({
	        contentType: "application/json",
	        type:'GET',
	        url:'<%=request.getContextPath()%>/user/idCheck.jsp',
	        dataType:'json',
	        data:param,
	        //data:JSON.stringify(param),
	        success: function (data) {
	            console.log(data);
	            if(data['rs'] === '0'){
	            	$('#isIdFalse').show();
	            	$('#isIdTrue').hide();
	            } else{
	            	$('#isIdTrue').show();
	            	$('#isIdFalse').hide();
	            };
	        },
	        error: function (request, status, error) {
	            console.log(request, status,error);
	        }
	    });
    }
}

$(function(){
	$('#isIdTrue').hide();
	$('#isIdFalse').hide();
	
	 // 입력 필드의 값이 변경될 때마다 idCheck 함수 호출
    $('#id').on('input', function() {
        idCheck();
    });
});
</script>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>회원가입</h1>
	<br>
	<form action="joinProc.usr" method="post" name="join">
		<table>
			<tr>
				<td><h3>아이디:</h3></td>
				<td><input type="text" name="id" id="id" class="form-control"
					placeholder="아이디를 입력해주세요" required></td>
				<td id="isIdTrue" style="color:red">중복된 아이디입니다.</td>
				<td id="isIdFalse" style="color:blue">사용 가능한 아이디입니다.</td>
				<td id="idLengthMessage" style="color:red"></td>
			</tr>
			<tr>
				<td><h3>비밀번호:</h3></td>
				<td><input type="password" name="pw" class="form-control"
					placeholder="비밀번호를 입력해주세요" required></td>
			</tr>
			<tr>
				<td><h3>이름:</h3></td>
				<td><input type="text" name="name" class="form-control"
					placeholder="이름을 입력해주세요" required></td>
			</tr>
			<tr>
				<td><h3>지역:</h3></td>
				<td>
					<h4>
					<select class="form-select" name="area" required>
					  <option selected>지역을 선택해주세요</option>
					  <option value="서울특별시">서울특별시</option>
					  <option value="경기도">경기도</option>
					  <option value="강원도">강원도</option>
					  <option value="대전광역시">대전광역시</option>
					  <option value="대구광역시">대구광역시</option>
					  <option value="부산광역시">부산광역시</option>
					  <option value="인천광역시">인천광역시</option>
					  <option value="광주광역시">광주광역시</option>
					  <option value="울산광역시">울산광역시</option>
					  <option value="세종특별시">세종특별시</option>
					  <option value="충청북도">충청북도</option>
					  <option value="충청남도">충청남도</option>
					  <option value="전라북도">전라북도</option>
					  <option value="전라남도">전라남도</option>
					  <option value="경상북도">경상북도</option>
					  <option value="경상남도">경상남도</option>
					  <option value="제주특별시">제주특별시</option>
					</select>
					</h4>
					
				</td>
			</tr>
<!-- 			<tr>
				<td><h3>역할:</h3></td>
				<td>
					<input type="radio" name="role" value="User" checked="checked">User
				</td>
				<td>
					<input type="radio" name="role" value="Admin">Admin<br>
				</td>
				</tr> -->
			<tr>
				<td><input type="submit" value="회원가입" class="btn btn-primary">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>