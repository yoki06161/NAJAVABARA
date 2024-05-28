package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proj.connect.HJDBCConnect;
import proj.dto.HUserDTO;

public class HUserDAO {
	
	public List<HUserDTO> getUsers(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<HUserDTO> userList = new ArrayList<HUserDTO>();
		
		try {
			conn = HJDBCConnect.getConnection();
			String sql = "select idx, id, pw, name, area, role, regdate from user";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String area = rs.getString("area");
				String role = rs.getString("role");
				String regdate = rs.getString("regdate");
				HUserDTO dto = new HUserDTO(idx, id, pw, name, area, role, regdate);
				userList.add(dto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			HJDBCConnect.close(rs, pstmt, conn);
		}
		
		return userList;
	}
	
	
	public HUserDTO getUser(HUserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = HJDBCConnect.getConnection();
			String sql = "select idx,id,pw,name,area,role,regdate from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			rs = pstmt.executeQuery(); // select

			dto = null;
			if (rs.next()) { // id 존재
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String area = rs.getString("area");
				String role = rs.getString("role");
				String regdate = rs.getString("regdate");
				dto = new HUserDTO(idx, id, pw, name, area, role, regdate);
			} 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HJDBCConnect.close(rs, pstmt, conn);
		}
		return dto;
	}
	
	
	public int insertUser(HUserDTO dto){
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       conn = HJDBCConnect.getConnection();
	       String sql = "insert into users(id,pw,name,area) values(?,?,?,?)";
	       pstmt = conn.prepareStatement(sql);
	       pstmt.setString(1, dto.getId());
	       pstmt.setString(2, dto.getPw());
	       pstmt.setString(3, dto.getName());
	       pstmt.setString(4, dto.getArea());
	       
	       rs = pstmt.executeUpdate();
	       System.out.println("rs>>>>>>>>>>>>>>"+rs);
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       HJDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}
	
	
	public int update(HUserDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0; 

		try{
			// 2. connection
			conn = HJDBCConnect.getConnection();
			
			// 3. sql 창
			String sql = "update users set pw=?, name=?, role=? where id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getPw());
			pstmt.setString(2,dto.getName());
			pstmt.setString(3,dto.getRole());
			pstmt.setString(4,dto.getId());
			// 4. execute
			rs = pstmt.executeUpdate();	// insert, update, delete
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HJDBCConnect.close(pstmt, conn);
		}
		return rs;
	}
}
