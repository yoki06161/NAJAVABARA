<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<%@ include file="../index.jsp"%>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<h1 class="text-center">회원가입</h1>
					</div>
					<div class="card-body">
						<form action="./register.do" method="post">
							<div class="form-group">
								<label for="id">아이디</label> <input type="text"
									class="form-control" id="id" name="id" required>
							</div>
							<div class="form-group">
								<label for="password">비밀번호</label> <input type="password"
									class="form-control" id="password" name="password" required>
							</div>
							<div class="form-group">
								<label for="name">이름</label> <input type="text"
									class="form-control" id="name" name="name">
							</div>
							<div class="form-group">
								<label for="role">역할</label> <select class="form-control"
									id="role" name="role" required>
									<option value="일반">일반</option>
									<option value="관리자">관리자</option>
								</select>
							</div>
							<button type="submit" class="btn btn-primary btn-block">가입</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>