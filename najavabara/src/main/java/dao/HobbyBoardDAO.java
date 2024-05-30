package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import common.JDBCConnect;
import dto.HobbyBoardDTO;


public class HobbyBoardDAO {
	
	public List<HobbyBoardDTO> selectFileList(Map<String, String> map) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// search 여부
		boolean isSearch = false;
		if (map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}
		
		List<HobbyBoardDTO> bbs = new ArrayList<HobbyBoardDTO>();
		String sql = "SELECT num, title, hobby, id, postdate, visitcount, orifile, newfile FROM hobbyBoard";
		if (isSearch) {
			sql += " WHERE " + map.get("searchField") + " LIKE ?";
		}
		sql += " ORDER BY num DESC";
		
		try {
			conn = JDBCConnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (isSearch) {
				pstmt.setString(1, "%" + map.get("searchWord") + "%");
			}
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String hobby = rs.getString("hobby");
				String id = rs.getString("id");
				String postdate = rs.getString("postdate");

				int visitcount = rs.getInt("visitcount");
				String orifile = rs.getString("orifile");
				String newfile = rs.getString("newfile");
				
				//System.out.println("Hobby: " + hobby);
				
				HobbyBoardDTO dto = new HobbyBoardDTO(num, title, hobby, id, postdate, visitcount, orifile, newfile);
				bbs.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		
		return bbs;
	}

	
	public int insertFileWrite(HobbyBoardDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    
	    try {
	    	String sql = "INSERT INTO hobbyBoard(title, id, hobby, content, orifile, newfile) VALUES(?, ?, ?, ?, ?, ?)";
	        conn = JDBCConnect.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getTitle());
	        pstmt.setString(2, dto.getId());
	        pstmt.setString(3, dto.getHobby());
	        pstmt.setString(4, dto.getContent());
	        pstmt.setString(5, dto.getOrifile());
	        pstmt.setString(6, dto.getNewfile());

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
	 
	
	public int updateFileWrite(HobbyBoardDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int rs = 0;

	    try {
	        conn = JDBCConnect.getConnection();
	        String sql = "UPDATE hobbyBoard SET title = ?, content = ?, orifile = ?, newfile = ? WHERE num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getTitle());
	        pstmt.setString(2, dto.getContent());
	        pstmt.setString(3, dto.getOrifile());
	        pstmt.setString(4, dto.getNewfile());
	        pstmt.setInt(5, dto.getNum());

	        rs = pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}


	public int deleteWrite(HobbyBoardDTO dto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = JDBCConnect.getConnection();
	       
	       // 3. sql + 쿼리창
	       String sql = "delete from hobbyBoard ";
	       sql += " where num = ?";
	       pstmt = conn.prepareStatement(sql);
	       
	       // 4. ? 세팅
	       pstmt.setInt(1, dto.getNum());
	       
	       // 5. execute 실행
	       rs = pstmt.executeUpdate();
	       
	    } catch (Exception e) {
	       e.printStackTrace();
	    }finally {
	       JDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}
	
	
	
    public HobbyBoardDTO selectView(HobbyBoardDTO dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM hobbyBoard where num = ?";

        try {
            conn = JDBCConnect.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getNum());
            rs = pstmt.executeQuery();

            dto = null;
            if (rs.next()) {
                int num = rs.getInt("num");
                String hobby = rs.getString("hobby");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String id = rs.getString("id");
                String postdate = rs.getString("postdate");

                int visitcount = rs.getInt("visitcount");
                String orifile = rs.getString("orifile");
                String newfile = rs.getString("newfile");

                dto = new HobbyBoardDTO(num, hobby, title, content, id, postdate, visitcount, orifile, newfile);
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

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard ";
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
	
	public HobbyBoardDTO getBoard(int num) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    HobbyBoardDTO dto = null;
	    
	    try {
	    	conn = JDBCConnect.getConnection();
	        String sql = "SELECT orifile FROM hobbyBoard WHERE num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, num);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	        	dto = new HobbyBoardDTO();
	            dto.setOrifile("orifile");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	JDBCConnect.close(rs, pstmt, conn);
	    }
	    
	    return dto;
	}

	public List<HobbyBoardDTO> selectmyList(Map<String, String> map, HttpSession session) {
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE id=?";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // 세션에서 id 값 가져오기
	        String id = (String) session.getAttribute("id");
	        pstmt.setString(1, id);

	        if (isSearch) {
	            pstmt.setString(2, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String postdate = rs.getString("postdate");
	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}

	
	
	public List<HobbyBoardDTO> selectGardeningList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='gardening'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}

	

	public List<HobbyBoardDTO> selectArtList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='art'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	public List<HobbyBoardDTO> selectCookList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='cook'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectPuzzleList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='puzzle'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectCollectionList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='collection'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectReadingList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='reading'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectExerciseList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='exercise'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectPhotoList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='photo'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectHandmadeList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='handmade'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectInstrumentList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='instrument'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}
	
	
	public List<HobbyBoardDTO> selectAstronomicalList(Map<String, String> map){
	    List<HobbyBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='astronomical'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = JDBCConnect.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        if (isSearch) {
	            pstmt.setString(1, "%" + map.get("searchWord") + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                String id = rs.getString("id");
	                String postdate = rs.getString("postdate");

	                int visitcount = rs.getInt("visitcount");
	                String orifile = rs.getString("orifile");
	                String newfile = rs.getString("newfile");

	                HobbyBoardDTO dto = new HobbyBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setOrifile(orifile);
	                dto.setNewfile(newfile);
	                bbs.add(dto);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bbs;
	}

	public int selectMineCount(Map<String, String> map, HttpSession session) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    int totalCount = 0;

	    // search 여부
	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();

	    String sql = "select count(num) as cnt from hobbyBoard where id=?";
	    if (isSearch) {
	        sql += " and " + map.get("searchField") + " like ?";
	    }
	    System.out.println(sql);
	    
	    try {
	        conn = JDBCConnect.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
	        // 세션에서 id 값 가져오기
	        String id = (String) session.getAttribute("id");
	        pstmt.setString(1, id);

	        if (isSearch) {
	            pstmt.setString(2, "%" + map.get("searchWord") + "%");
	        }

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            totalCount = rs.getInt("cnt");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCConnect.close(rs, pstmt, conn);
	    }

	    return totalCount;
	}

	
	public int selectGardeningCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='gardening'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	
	public int selectArtCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='art'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	public int selectCookCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				
		
		int totalCount = 0;
		
		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		
		
		String sql = "select count(num) as cnt from hobbyBoard where hobby='art'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	public int selectPuzzleCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='puzzle'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	public int selectCollectionCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='collection'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	
	public int selectReadingCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='reading'";
		if(isSearch) {
			sql += " and " + map.get("searchField") + " like ? ";
		}
		System.out.println(sql);

		try {
			conn = JDBCConnect.getConnection();
			pstmt = conn.prepareStatement(sql);
			if(isSearch) {
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
	
	
	public int selectExerciseCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='exercise'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	
	public int selectPhotoCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='photo'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	
	public int selectHandmadeCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='handmade'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	
	public int selectInstrumentCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='instrument'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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
	
	
	public int selectAstronomicalCount(Map<String, String> map){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;

		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from hobbyBoard where hobby='astronomical'";
		if(isSearch) {
			//sql += " and " + map.get("searchField") + " like concat('%',?,'%')";
			sql += " and " + map.get("searchField") + " like ? ";
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




}