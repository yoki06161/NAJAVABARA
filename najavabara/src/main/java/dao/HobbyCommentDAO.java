package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dto.HobbyBoardDTO;
import dto.HobbyCommentDTO;
import common.JDBCConnect;


public class HobbyCommentDAO {
	
	public List<HobbyCommentDTO> commentLists(int num) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    HobbyCommentDTO cto = new HobbyCommentDTO();
	    List<HobbyCommentDTO> cbs = new ArrayList<HobbyCommentDTO>();
	    String sql = "SELECT * FROM hobbyComments WHERE num=? ORDER BY numx DESC;";
	    try {
	        conn = JDBCConnect.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, num);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	int numx = rs.getInt("numx");
	            int snum = rs.getInt("num");
	            String content = rs.getString("content");
	            String id = rs.getString("id");
	            String postdate = rs.getString("postdate");

	            cto = new HobbyCommentDTO(numx, snum, id, postdate, content);
	            cbs.add(cto);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCConnect.close(rs, pstmt, conn);
	    }

	    return cbs;
	}


	
	public int selectCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyComments ";
		if(isSearch) {
			sql += " where " + map.get("searchField") + " like ? ";
		}
		System.out.println(sql);

		try {
			conn = JDBCConnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			if(isSearch) {
				//pstmt.setString(1, map.get("searchWord"));
				pstmt.setString(1, "%" + map.get("searchWord") + "%");
			}
			rs = pstmt.executeQuery();

			if(rs.next()) {
				totalCount = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}		

		return totalCount;
	}
	

	public int insertCommentWrite(HobbyCommentDTO cto, HobbyBoardDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 쿼리창
	       String sql = "insert into hobbyComments(id, num, content) values(?,?,?)";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. ? 세팅
	       pstmt.setString(1, cto.getId());
	       pstmt.setInt(2, dto.getNum());
	       pstmt.setString(3, cto.getContent());
	       
	       // 5. execute 실행
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    } finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}


	
	 public void updateVisitcount(int num) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        
	        String sql = "update hobbyBoard set visitcount = visitcount + 1 WHERE num = ?";
	        
	        try {
	            conn = JDBCConnect.getConnection();
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, num);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            JDBCConnect.close(pstmt, conn);
	        }
	    }
	 

	public int updateWrite(HobbyCommentDTO cto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 쿼리창
	       String sql = "update hobbyBoard set content = ? ";
	       sql += " where num = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. ? 세팅
	       pstmt.setString(1, cto.getContent());
	       pstmt.setInt(2, cto.getNum());
	       
	       // 5. execute 실행
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

	public int delete(HobbyCommentDTO cto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 쿼리창
	       String sql = "delete from hobbyComments ";
	       sql += " where numx = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. ? 세팅
	       pstmt.setInt(1, cto.getNumx());
	       
	       // 5. execute 실행
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}
	
	public int deleteAll(HobbyCommentDTO cto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 쿼리창
	       String sql = "delete from hobbyComments ";
	       sql += " where num = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. ? 세팅
	       pstmt.setInt(1, cto.getNum());
	       
	       // 5. execute 실행
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

}
