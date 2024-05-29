package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBCConnect;
import dto.BoardDTO;

public class BoardDAO {
	
	public List<BoardDTO> selectList(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		// search 
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}	

		List<BoardDTO> bbs = new ArrayList<BoardDTO>();
		//sql
		String sql = "select num,title,content,id,postdate,visitcount from freeBoard ";
		if(isSearch) {
			sql += " where " + map.get("searchField") + " like ? ";
		}
		sql += " order by num desc";


		try {
			conn = JDBCConnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			if(isSearch) {
				pstmt.setString(1, "%" + map.get("searchWord") + "%");
			}
			rs = pstmt.executeQuery();

			while(rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String postdate = rs.getString("postdate");
				int visitcount = rs.getInt("visitcount");
				
				BoardDTO dto = new BoardDTO(num, title, content, id, postdate, visitcount);
				bbs.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return bbs;


	}
	
	// �룯占� 野껊슣�뻻�눧占�
	public List<BoardDTO> selectPageList(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		// search 
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}	

		List<BoardDTO> bbs = new ArrayList<BoardDTO>();
		String sql = "select num,title,content,id,postdate,visitcount from freeBoard ";
		if(isSearch) {
			sql += " where " + map.get("searchField") + " like ? ";
		}
		sql += " order by num desc ";
		sql += " limit ? offset ?"; // 2page


		try {
			conn = JDBCConnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			if(isSearch) {
				pstmt.setString(1, "%" + map.get("searchWord") + "%");
			}
			pstmt.setInt(1, Integer.parseInt(map.get("amount")));
			pstmt.setInt(2, Integer.parseInt(map.get("offset")));
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String postdate = rs.getString("postdate");
				int visitcount = rs.getInt("visitcount");
				
				BoardDTO dto = new BoardDTO(num, title, content, id, postdate, visitcount);
				bbs.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}



		return bbs;
	}

	
	public BoardDTO selectView(BoardDTO dto){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		String sql = "select num,title,content,A.id,postdate,visitcount,name ";
		sql += " from freeBoard A, user B ";
		sql += " where num = ? and A.id = B.id";

		try {
			conn = JDBCConnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			rs = pstmt.executeQuery();
			
			dto = null;
			if(rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String postdate = rs.getString("postdate");
				int visitcount = rs.getInt("visitcount");
				String name = rs.getString("name");
				dto = new BoardDTO(num, title, content, id, postdate, visitcount, name);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		
		return dto;
	}
	
	public int selectCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from freeBoard ";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
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

	public int insertWrite(BoardDTO dto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 
	       String sql = "insert into freeBoard(title,content,id) values(?,?,?)";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4.
	       pstmt.setString(1, dto.getTitle());
	       pstmt.setString(2, dto.getContent());
	       pstmt.setString(3, dto.getId());
	       
	       // 5. execute 
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

	
	public int insertFileWrite(BoardDTO dto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 
	       String sql = "insert into freeBoard(title,content,id) values(?,?,?,?,?)";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. 
	       pstmt.setString(1, dto.getTitle());
	       pstmt.setString(2, dto.getContent());
	       pstmt.setString(3, dto.getId());
	       
	       // 5. execute 
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

	
	public int updateVisitcount(BoardDTO dto) {
	// 	硫붿냼�뱶 �븞
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
		// 2. conn
	       conn = JDBCConnect.getConnection();
	       
	  //     // 3. sql + 荑쇰━李�
	       String sql = "update freeBoard set visitcount = visitcount + 1 ";
	       sql += " where num = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. �꽭�똿
	       pstmt.setInt(1, dto.getNum());
	       
	       // 5. execute �떎�뻾
	       rs = pstmt.executeUpdate();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

	public int updateWrite(BoardDTO dto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 荑쇰━李�
	       String sql = "update freeBoard set title = ?, content = ? ";
	       sql += " where num = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. �꽭�똿
	       pstmt.setString(1, dto.getTitle());
	       pstmt.setString(2, dto.getContent());
	       pstmt.setInt(3, dto.getNum());
	       
	       // 5. execute �떎�뻾
	       rs = pstmt.executeUpdate();
	       
	       System.out.println(rs + ": sql臾� �꽦怨� �뿬遺�");
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}

	public int deleteWrite(BoardDTO dto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 荑쇰━李�
	       String sql = "delete from freeBoard ";
	       sql += " where num = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. �꽭�똿
	       pstmt.setInt(1, dto.getNum());
	       
	       // 5. execute �떎�뻾
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}
}
