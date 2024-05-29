package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.JDBCConnect;
import dto.UserDTO;

public class UserDAO {
	// 아이디로 사용자 가져오기
	public UserDTO getUser (UserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// select문에서만 사용

		try {
			// connection
			conn = JDBCConnect.getConnection();

			// sql 창- 리스트면 where ..=?부분 삭제 
			String sql = "select idx, id, pw, name, area, role, regdate from user where id=?";
			pstmt = conn.prepareStatement(sql);
			// 문자니까 setString, 날짜면 setDate 등등 ...
			// 리스트면 이걸 삭제
			pstmt.setString(1, dto.getId());
			// execute
			rs = pstmt.executeQuery(); 

			// 있는지 판단 - 리스트면 이걸 삭제 
			dto = null;
			// List<DTO명>이면 if를 while로 변경
			if (rs.next()) { // id 존재
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String area = rs.getString("area");
				String role = rs.getString("role");
				String regDate = rs.getString("regdate");
				// 생성자 필요에 따라 추가(리스트면 dto앞에 DTO명 붙여야함)
				dto = new UserDTO(idx, id, pw, name, area, regDate);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return dto;
	} 

	// 사용자 회원가입
	public void insertUser (UserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;  

		try {
			// conn
			conn = JDBCConnect.getConnection();

			// sql + 쿼리창
			String sql = "insert into user(id, pw, name, area) values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			// ?에 들어갈 컬럼들 세팅
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getArea());

			// execute 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
	}

	// 사용자 정보 수정
	public int updateUser (UserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;   

		try {
			// conn
			conn = JDBCConnect.getConnection();

			// sql + 쿼리창
			String sql = "update user set pw=?, name=?, area=? where id=?";
			pstmt = conn.prepareStatement(sql);

			// ? 세팅
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getArea());
			pstmt.setString(4, dto.getId());

			// execute 실행
			rs = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
		return rs;
	} 

	public int deleteUser (UserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;   

		try {
			// 2. conn
			conn = JDBCConnect.getConnection();

			// sql + 쿼리창
			String sql = "delete from user where id=?";
			pstmt = conn.prepareStatement(sql);

			// ? 세팅
			pstmt.setString(1, dto.getId());

			// execute 실행
			rs = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
		return rs;
	}
}
