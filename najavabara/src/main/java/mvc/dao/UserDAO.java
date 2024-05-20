package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mvc.common.JDBCConnect;
import mvc.dto.UserDTO;

public class UserDAO {
	
	public List<UserDTO> getUsers(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		

		List<UserDTO> userList = new ArrayList<>();

		try{
			// 2. connection
			conn = JDBCConnect.getConnection();
			
			
			// 3. sql문
			String sql = "select idx, id, pw, name, area, role, regdate from user ";
			pstmt = conn.prepareStatement(sql);
			// 4. execute
			rs = pstmt.executeQuery();
			// 5. rs 처리 : id값만 list에 저장
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String password = rs.getString("pw");
				String name = rs.getString("name");
				String area = rs.getString("area");
				String role = rs.getString("role");
				String regDate = rs.getString("regdate");
				UserDTO dto = new UserDTO(idx, id, password, name, role, area, regDate);
				userList.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return userList;
	}
	
	public List<UserDTO> getUsers(int listNum, int offset){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		

		List<UserDTO> userList = new ArrayList<>();

		try{
			// 2. connection
			conn = JDBCConnect.getConnection();			
			
			// 3. sql문
			String sql = "select idx, id, pw, name, area, role, regdate from user ";
			sql += " limit ? offset ?"; // 2page
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, listNum);
			pstmt.setInt(2, offset);
			// 4. execute
			rs = pstmt.executeQuery();
			// 5. rs 처리 : id값만 list에 저장
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String password = rs.getString("pw");
				String name = rs.getString("name");
				String area = rs.getString("area");
				String role = rs.getString("role");
				String regDate = rs.getString("regdate");
				UserDTO dto = new UserDTO(idx, id, password, name, area, role, regDate);
				userList.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return userList;
	}
	
	
	public UserDTO getUser(UserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // select , 회원가입은 insert할 것이므로 주석 !

		try {
			// 2. connection
			conn = JDBCConnect.getConnection();
			
			// 3. sql문
			String sql = "select idx, id, pw, name, area, role, regdate from user where id=?";
			pstmt = conn.prepareStatement(sql);
			// 문자니까 setString, 날짜면 setDate 등등 ...
			pstmt.setString(1, dto.getId());
			// 4. execute
			rs = pstmt.executeQuery(); // select

			// 있는지 판단
			dto = null;
			if (rs.next()) { // id 존재
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String password = rs.getString("pw");
				String name = rs.getString("name");
				String area = rs.getString("area");
				String role = rs.getString("role");
				String regDate = rs.getString("regdate");
				dto = new UserDTO(idx, id, password, name, area, role, regDate);
			} 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return dto;
	}
	
	public int insertUser(UserDTO dto){
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 쿼리문
	       String sql = "insert into user(id, pw, name, role) values(?,?,?,?)";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. ? 세팅
	       pstmt.setString(1, dto.getId());
	       pstmt.setString(2, dto.getPw());
	       pstmt.setString(3, dto.getName());
	       pstmt.setString(4, dto.getRole());
	       
	       // 5. execute 실행
	       rs = pstmt.executeUpdate();
	       System.out.println("rs>>>>>>>>>>>>>>"+rs);
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}
	
	
	public int updateUser(UserDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int rs = 0; 

	    try {
	        // 2. connection
	        conn = JDBCConnect.getConnection();
	        
	        // 3. sql문
	        String sql = "UPDATE user SET pw=?, name=? WHERE id=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getPw());
	        pstmt.setString(2, dto.getName());
	        pstmt.setString(3, dto.getId());
	        
	        // 4. execute
	        rs = pstmt.executeUpdate(); // insert, update, delete
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

	
	public int delete(UserDTO dto) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // select

		try {
			// 2. connection
			conn = JDBCConnect.getConnection();
			
			// 3. sql문,  삭제 처리
			String	sql = "delete from user where id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			// 4. execute
			result = pstmt.executeUpdate(); // insert, update, delete


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(pstmt, conn);
			
		}
		
		return result;
	}

}
