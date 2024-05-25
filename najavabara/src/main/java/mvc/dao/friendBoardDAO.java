package mvc.dao;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mvc.common.JDBCConnect;
import mvc.dto.CommentDTO;
import mvc.dto.friendBoardDTO;

public class friendBoardDAO {
	
	
	 public List<friendBoardDTO> selectPageList(Map<String, String> map) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        boolean isSearch = false;
	        if (map.get("searchWord") != null && !map.get("searchWord").isEmpty()) {
	            isSearch = true;
	        }

	        boolean isFilterArea = false;
	        if (map.get("searchArea") != null && !map.get("searchArea").isEmpty()) {
	            isFilterArea = true;
	        }

	        List<friendBoardDTO> postList = new ArrayList<>();

	        String sql = "SELECT p.num, p.title, p.content, p.postdate, p.visitcount, p.id, p.area, " +
	                     "GROUP_CONCAT(f.fileName SEPARATOR ',') AS fileNames, COUNT(c.commentNum) AS commentCount " +
	                     "FROM friendBoard p " +
	                     "LEFT JOIN comments c ON p.num = c.postNum " +
	                     "LEFT JOIN friendBoardFiles f ON p.num = f.postNum ";

	        if (isSearch) {
	            sql += "WHERE " + map.get("searchField") + " LIKE ? ";
	        }
	        if (isFilterArea) {
	            if (isSearch) {
	                sql += "AND ";
	            } else {
	                sql += "WHERE ";
	            }
	            sql += "p.area = ? ";
	        }
	        sql += "GROUP BY p.num ORDER BY p.num DESC ";
	        sql += "LIMIT ? OFFSET ?";

	        conn = JDBCConnect.getConnection();

	        try {
	            pstmt = conn.prepareStatement(sql);
	            int parameterIndex = 1;

	            if (isSearch) {
	                pstmt.setString(parameterIndex++, "%" + map.get("searchWord") + "%");
	            }
	            if (isFilterArea) {
	                pstmt.setString(parameterIndex++, map.get("searchArea"));
	            }
	            pstmt.setInt(parameterIndex++, Integer.parseInt(map.get("amount")));
	            pstmt.setInt(parameterIndex++, Integer.parseInt(map.get("offset")));

	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                int num = rs.getInt("num");
	                String title = rs.getString("title");
	                String content = rs.getString("content");
	                Timestamp postdate = rs.getTimestamp("postdate");
	                int visitcount = rs.getInt("visitcount");
	                String id = rs.getString("id");
	                int commentCount = rs.getInt("commentCount");
	                String area = rs.getString("area");
	                String fileNamesStr = rs.getString("fileNames");
	                List<String> fileNames = new ArrayList<>();
	                if (fileNamesStr != null && !fileNamesStr.isEmpty()) {
	                    for (String fileName : fileNamesStr.split(",")) {
	                        fileNames.add(fileName.trim());
	                    }
	                }

	                friendBoardDTO dto = new friendBoardDTO(num, title, content, id, postdate, visitcount);
	                dto.setCommentCount(commentCount);
	                dto.setFileNames(fileNames);
	                dto.setArea(area);

	                postList.add(dto);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            JDBCConnect.close(rs, pstmt, conn);
	        }

	        return postList;
	    }
	
	 public friendBoardDTO selectView(friendBoardDTO dto) {
		    Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;

		    String sql = "SELECT p.num, p.title, p.content, p.postdate, p.visitcount, p.id, p.area, " +
		                 "GROUP_CONCAT(f.fileName SEPARATOR ',') AS fileNames, " +
		                 "GROUP_CONCAT(f.ofileName SEPARATOR ',') AS ofileNames " +
		                 "FROM friendBoard p " +
		                 "LEFT JOIN friendBoardFiles f ON p.num = f.postNum " +
		                 "WHERE p.num = ? " +
		                 "GROUP BY p.num";
		    conn = JDBCConnect.getConnection();

		    try {
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, dto.getNum());
		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            int num = rs.getInt("num");
		            String title = rs.getString("title");
		            String content = rs.getString("content");
		            String id = rs.getString("id");
		            Timestamp postdate = rs.getTimestamp("postdate");
		            int visitcount = rs.getInt("visitcount");
		            String area = rs.getString("area");
		            String fileNamesStr = rs.getString("fileNames");
		            String ofileNamesStr = rs.getString("ofileNames");
		            List<String> fileNames = new ArrayList<>();
		            List<String> ofileNames = new ArrayList<>();
		            if (fileNamesStr != null && !fileNamesStr.isEmpty()) {
		                for (String fileName : fileNamesStr.split(",")) {
		                    fileNames.add(fileName.trim());
		                }
		            }
		            if (ofileNamesStr != null && !ofileNamesStr.isEmpty()) {
		                for (String ofileName : ofileNamesStr.split(",")) {
		                    ofileNames.add(ofileName.trim());
		                }
		            }
		            dto = new friendBoardDTO(num, title, content, id, postdate, visitcount);
		            dto.setFileNames(fileNames);
		            dto.setOfileNames(ofileNames);
		            dto.setArea(area);
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

		String sql = "select count(num) as cnt from friendBoard ";
		if(isSearch) {
			sql += " where " + map.get("searchField") + " like ? ";
		}
		System.out.println(sql);
		conn = JDBCConnect.getConnection();

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
			JDBCConnect.close(rs, pstmt, conn);
		}       

		return totalCount;
	}

	 public int insertWrite(friendBoardDTO dto) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        int result = 0;

	        try {
	            conn = JDBCConnect.getConnection();
	            String sql = "INSERT INTO friendBoard (title, content, id, area) VALUES (?, ?, ?, ?)";
	            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	            pstmt.setString(1, dto.getTitle());
	            pstmt.setString(2, dto.getContent());
	            pstmt.setString(3, dto.getId());
	            pstmt.setString(4, dto.getArea());

	            result = pstmt.executeUpdate();

	            if (result > 0) {
	                rs = pstmt.getGeneratedKeys();
	                if (rs.next()) {
	                    int postNum = rs.getInt(1);
	                    insertFiles(postNum, dto.getFileNames(), dto.getOfileNames());
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            JDBCConnect.close(rs, pstmt, conn);
	        }
	        return result;
	    }
	 
	// 파일명 삽입
	 private void insertFiles(int postNum, List<String> fileNames, List<String> ofileNames) throws SQLException {
		    Connection conn = null;
		    PreparedStatement pstmt = null;

		    try {
		        // 데이터베이스 연결
		        conn = JDBCConnect.getConnection();
		        // SQL 쿼리 준비
		        String sql = "INSERT INTO friendBoardFiles (postNum, fileName, ofileName) VALUES (?, ?, ?)";
		        pstmt = conn.prepareStatement(sql);

		        // 각 파일에 대한 값 추가
		        for (int i = 0; i < fileNames.size(); i++) {
		            pstmt.setInt(1, postNum); // 게시물 번호 설정
		            pstmt.setString(2, fileNames.get(i)); // 파일 이름 설정
		            pstmt.setString(3, ofileNames.get(i)); // 원본 파일 이름 설정
		            pstmt.addBatch(); // 일괄 처리에 추가
		        }

		        pstmt.executeBatch(); // 일괄 처리 실행

		    } finally {
		        // 데이터베이스 연결 종료
		        JDBCConnect.close(pstmt, conn);
		    }
		}

	// 조회수 증가
	    public int updateVisitcount(friendBoardDTO dto) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        int rs = 0;
	        try {
	            conn = JDBCConnect.getConnection();

	            String sql = "UPDATE friendBoard SET visitcount = visitcount + 1 WHERE num = ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, dto.getNum());

	            rs = pstmt.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            JDBCConnect.close(pstmt, conn);
	        }
	        return rs;
	    }

	// 게시물 수정
    public int updateWrite(friendBoardDTO dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rs = 0;
        try {
            conn = JDBCConnect.getConnection();

            String sql = "UPDATE friendBoard SET title = ?, content = ?, area = ? WHERE num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getArea());
            pstmt.setInt(4, dto.getNum());

            rs = pstmt.executeUpdate();

            if (rs > 0 && dto.getFileNames() != null && !dto.getFileNames().isEmpty()) {
                deleteFiles(dto.getNum());
                insertFiles(dto.getNum(), dto.getFileNames(), dto.getOfileNames());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }
        return rs;
    }

	public int deleteWrite(friendBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;  
		int rs = 0;
		try {
			// 1. conn
			conn = JDBCConnect.getConnection();

			// 2. 댓글 삭제
			String deleteCommentsSql = "DELETE FROM comments WHERE postNum = ?";
			pstmt = conn.prepareStatement(deleteCommentsSql);
			pstmt.setInt(1, dto.getNum());
			pstmt.executeUpdate();
			pstmt.close();

			// 3. 게시물 삭제
			String deletePostSql = "DELETE FROM friendBoard WHERE num = ?";
			pstmt = conn.prepareStatement(deletePostSql);
			pstmt.setInt(1, dto.getNum());

			// 4. execute 실행
			rs = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(pstmt, conn);
		}
		return rs;
	}

	// 파일 삭제
    private void deleteFiles(int postNum) throws SQLException {
        Connection conn  = null;
        PreparedStatement pstmt = null;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "DELETE FROM friendBoardFiles WHERE postNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postNum);
            pstmt.executeUpdate();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }
    }
	
    public static friendBoardDTO getPostByNum(String postNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        friendBoardDTO post = null;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT p.*, f.fileName, f.ofileName FROM friendBoard p " +
                         "LEFT JOIN friendBoardFiles f ON p.num = f.postNum " +
                         "WHERE p.num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, postNum);
            rs = pstmt.executeQuery();

            List<String> fileNames = new ArrayList<>();
            List<String> ofileNames = new ArrayList<>();

            while (rs.next()) {
                if (post == null) { // 첫 번째 행만 post 객체 생성
                    int num = rs.getInt("num");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String id = rs.getString("id");
                    Timestamp postdate = rs.getTimestamp("postdate");
                    int visitcount = rs.getInt("visitcount");
                    String area = rs.getString("area"); // 'area'가 DTO의 필드라고 가정합니다.
                    post = new friendBoardDTO(num, title, content, id, postdate, visitcount, area);
                }

                // 파일명 추가
                String fileName = rs.getString("fileName");
                String ofileName = rs.getString("ofileName");
                fileNames.add(fileName);
                ofileNames.add(ofileName);
            }

            // 파일명 리스트를 post 객체에 설정
            post.setFileNames(fileNames);
            post.setOfileNames(ofileNames);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return post;
    }

    public List<friendBoardDTO> getPostByUserId(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<friendBoardDTO> postList = new ArrayList<>();

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT * FROM friendBoard WHERE id = ? ORDER BY num DESC ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int num = rs.getInt("num");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Timestamp postdate = rs.getTimestamp("postdate");
                int visitcount = rs.getInt("visitcount");
                int commentCount = rs.getInt("commentCount");

                friendBoardDTO post = new friendBoardDTO(num, title, content, userId, postdate, visitcount);
                post.setCommentCount(commentCount);
                postList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return postList;
    }

    public friendBoardDTO findPostByComment(CommentDTO comment, List<friendBoardDTO> postList) {
        for (friendBoardDTO post : postList) {
            if (post.getNum() == comment.getPostNum()) {
                return post;
            }
        }
        return null;
    }

    public int incrementLikeCount(int postNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int affectedRows = 0;

        try {
            conn = JDBCConnect.getConnection();

            // 해당 게시물의 좋아요 수를 증가시키는 SQL 쿼리
            String sql = "UPDATE friendBoard SET likeCount = likeCount + 1 WHERE num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postNum);

            // 쿼리 실행 후 영향 받은 행의 수를 가져옵니다.
            affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }

        return affectedRows;
    }

    public int selectLikeCount(int num) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int likeCount = 0;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT likeCount FROM friendBoard WHERE num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                likeCount = rs.getInt("likeCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return likeCount;
    }

    public void decrementLikeCount(int num) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "UPDATE friendBoard SET likeCount = likeCount - 1 WHERE num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }
    }

    public boolean isFileLocked(File file) {
        try (FileChannel channel = new RandomAccessFile(file, "rw").getChannel()) {
            try {
                channel.lock();
                return false;
            } catch (OverlappingFileLockException e) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean deleteFileIfExists(File file) {
        if (file.exists()) {
            if (!isFileLocked(file)) {
                // 파일이 잠겨 있지 않으면 파일 삭제 시도
                if (file.delete()) {
                    System.out.println("파일이 성공적으로 삭제되었습니다: " + file.getAbsolutePath());
                    return true;
                } else {
                    System.out.println("파일 삭제 실패: " + file.getAbsolutePath());
                    return false;
                }
            } else {
                System.out.println("파일이 잠겨 있습니다: " + file.getAbsolutePath());
                return false;
            }
        } else {
            System.out.println("파일이 존재하지 않습니다: " + file.getAbsolutePath());
            return false;
        }
    }
}
