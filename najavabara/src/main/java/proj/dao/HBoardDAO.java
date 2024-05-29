package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import proj.connect.HJDBCConnect;
import proj.dto.HBoardDTO;


public class HBoardDAO {
	
	public List<HBoardDTO> selectFileList(Map<String, String> map) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// search 여부
		boolean isSearch = false;
		if (map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}
		
		List<HBoardDTO> bbs = new ArrayList<HBoardDTO>();
		String sql = "SELECT num, title, hobby, id, postdate, visitcount, orifile, newfile FROM hobbyBoard";
		if (isSearch) {
			sql += " WHERE " + map.get("searchField") + " LIKE ?";
		}
		sql += " ORDER BY num DESC";
		
		try {
			conn = HJDBCConnect.getConnection();
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
				
				HBoardDTO dto = new HBoardDTO(num, title, hobby, id, postdate, visitcount, orifile, newfile);
				bbs.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			HJDBCConnect.close(rs, pstmt, conn);
		}
		
		return bbs;
	}

	
	public int insertFileWrite(HBoardDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    
	    try {
	    	String sql = "INSERT INTO hobbyBoard(title, id, hobby, content, postdate, orifile, newfile) VALUES(?, ?, ?, ?, NOW(), ?, ?)";
	        conn = HJDBCConnect.getConnection();
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
	       HJDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}


	
	 public void updateVisitcount(int num) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        
	        String sql = "update hobbyBoard set visitcount = visitcount + 1 WHERE num = ?";
	        
	        try {
	            conn = HJDBCConnect.getConnection();
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, num);
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            HJDBCConnect.close(pstmt, conn);
	        }
	    }
	 
	
	public int updateFileWrite(HBoardDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    int rs = 0;

	    try {
	        conn = HJDBCConnect.getConnection();
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
	        HJDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}


	public int deleteWrite(HBoardDTO dto) {
		Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	       // 2. conn
	       conn = HJDBCConnect.getConnection();
	       
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
	       HJDBCConnect.close(pstmt, conn);
	    }
	    return rs;
	}
	
	
	
    public HBoardDTO selectView(HBoardDTO dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM hobbyBoard where num = ?";

        try {
            conn = HJDBCConnect.getConnection();
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

                dto = new HBoardDTO(num, hobby, title, content, id, postdate, visitcount, orifile, newfile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
		}		

		return totalCount;
	}
	
	public HBoardDTO getBoard(int num) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    HBoardDTO dto = null;
	    
	    try {
	    	conn = HJDBCConnect.getConnection();
	        String sql = "SELECT orifile FROM hobbyBoard WHERE num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, num);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	        	dto = new HBoardDTO();
	            dto.setOrifile("orifile");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	    	HJDBCConnect.close(rs, pstmt, conn);
	    }
	    
	    return dto;
	}

	
	
	
	public List<HBoardDTO> selectGardeningList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='gardening'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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

	

	public List<HBoardDTO> selectArtList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='art'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectPuzzleList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='puzzle'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectCollectionList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='collection'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectReadingList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='reading'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectExerciseList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='exercise'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectPhotoList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='photo'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectHandmadeList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='handmade'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectInstrumentList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='instrument'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
	
	
	public List<HBoardDTO> selectAstronomicalList(Map<String, String> map){
	    List<HBoardDTO> bbs = new ArrayList<>();
	    String sql = "SELECT num, title, content, id, postdate, visitcount, orifile, newfile FROM hobbyBoard WHERE hobby='astronomical'";

	    boolean isSearch = map.get("searchWord") != null && !map.get("searchWord").isEmpty();
	    if (isSearch) {
	        sql += " AND " + map.get("searchField") + " LIKE ?";
	    }
	    sql += " ORDER BY num DESC";

	    try (Connection conn = HJDBCConnect.getConnection();
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

	                HBoardDTO dto = new HBoardDTO(num, title, content, id, postdate, visitcount);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
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
		conn = HJDBCConnect.getConnection();

		try {
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
			HJDBCConnect.close(rs, pstmt, conn);
		}		

		return totalCount;
	}




}