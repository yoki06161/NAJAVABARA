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

	    // search 여부
	    boolean isSearch = false;
	    if (map.get("searchWord") != null && !map.get("searchWord").isEmpty()) {
	        isSearch = true;
	    }

	    // 지역 필터 여부
	    boolean isFilterArea = false;
	    if (map.get("searchArea") != null && !map.get("searchArea").isEmpty()) {
	        isFilterArea = true;
	    }

	    List<friendBoardDTO> postList = new ArrayList<>();

	    // 기본 SQL 쿼리
	    String sql = "SELECT p.num, p.title, p.content, p.postdate, p.visitcount, p.id, p.fileName, p.area, COUNT(c.commentNum) AS commentCount " +
	                 "FROM friendBoard p LEFT JOIN comments c ON p.num = c.postNum ";
	    // 검색어를 포함한 경우 조건 추가
	    if (isSearch) {
	        sql += "WHERE " + map.get("searchField") + " LIKE ? ";
	    }
	    // 지역 필터링 조건 추가
	    if (isFilterArea) {
	        if (isSearch) {
	            sql += "AND ";
	        } else {
	            sql += "WHERE ";
	        }
	        sql += "p.area = ? ";
	    }
	    sql += "GROUP BY p.num ORDER BY p.num DESC ";
	    sql += "LIMIT ? OFFSET ?"; // LIMIT과 OFFSET을 사용하여 페이지별로 결과를 제한

	    conn = JDBCConnect.getConnection();

	    try {
	        pstmt = conn.prepareStatement(sql);
	        int parameterIndex = 1; // 파라미터 인덱스

	        if (isSearch) {
	            pstmt.setString(parameterIndex++, "%" + map.get("searchWord") + "%");
	        }
	        // 지역 필터링 파라미터 설정
	        if (isFilterArea) {
	            pstmt.setString(parameterIndex++, map.get("searchArea"));
	        }
	        // LIMIT과 OFFSET 파라미터 설정
	        pstmt.setInt(parameterIndex++, Integer.parseInt(map.get("amount"))); // LIMIT
	        pstmt.setInt(parameterIndex++, Integer.parseInt(map.get("offset"))); // OFFSET

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int num = rs.getInt("num");
	            String title = rs.getString("title");
	            String content = rs.getString("content");
	            Timestamp postdate = rs.getTimestamp("postdate");
	            int visitcount = rs.getInt("visitcount");
	            String id = rs.getString("id");
	            String fileName = rs.getString("fileName");
	            int commentCount = rs.getInt("commentCount");
	            String area = rs.getString("area");

	            // 새로운 생성자 사용하여 객체 생성
	            friendBoardDTO dto = new friendBoardDTO(num, title, content, id, postdate, visitcount);
	            dto.setFileName(fileName);
	            dto.setCommentCount(commentCount);
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
	
	public friendBoardDTO selectView(friendBoardDTO dto){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;                

		String sql = "select num, title, content, postdate, visitcount, A.id, fileName ";
		sql += "from friendBoard A, user B ";
		sql += "where num = ? and A.id = B.id";
		conn = JDBCConnect.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			rs = pstmt.executeQuery();

			dto = null;
			if (rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Timestamp postdate = rs.getTimestamp("postdate");
				int visitcount = rs.getInt("visitcount");
				String fileName = rs.getString("fileName");
				dto = new friendBoardDTO(num, title, content, id, postdate, visitcount, fileName);
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
		int result = 0;

		try {
			conn = JDBCConnect.getConnection();
			String sql = "INSERT INTO friendBoard (title, content, id, filename, area) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getFileName());
			pstmt.setString(5, dto.getArea());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(pstmt, conn);
		}
		return result;
	}

	// 조회수 증가
	public int updateVisitcount(friendBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;  
		int rs = 0;
		try {
			// 2. conn
			conn = JDBCConnect.getConnection();

			// 3. sql + 쿼리창
			String sql = "update friendBoard set visitcount = visitcount + 1 ";
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

	public int updateWrite(friendBoardDTO dto) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;  
	    int rs = 0;
	    try {
	        // 2. conn
	        conn = JDBCConnect.getConnection();

	        // 3. sql + 쿼리창
	        String sql = "UPDATE friendBoard SET title = ?, content = ?";
	        if (dto.getFileName() != null && !dto.getFileName().isEmpty()) {
	            sql += ", fileName = ?"; // 파일명이 있을 경우에만 파일명 업데이트
	        }
	        sql += " WHERE num = ?";
	        pstmt = conn.prepareStatement(sql);

	        // 4. ? 세팅
	        pstmt.setString(1, dto.getTitle());
	        pstmt.setString(2, dto.getContent());

	        if (dto.getFileName() != null && !dto.getFileName().isEmpty()) {
	            pstmt.setString(3, dto.getFileName());
	            pstmt.setInt(4, dto.getNum());
	        } else {
	            pstmt.setInt(3, dto.getNum());
	        }

	        // 5. execute 실행
	        rs = pstmt.executeUpdate();

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

	public static friendBoardDTO getPostByNum(String postNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		friendBoardDTO post = null;

		try {
			conn = JDBCConnect.getConnection();
			String sql = "SELECT * FROM friendBoard WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, postNum);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Timestamp postdate = rs.getTimestamp("postdate");
				int visitcount = rs.getInt("visitcount");
				String fileName = rs.getString("fileName");
				post = new friendBoardDTO(num, title, content, id, postdate, visitcount, fileName);
			}
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

			// 해당 게시물의 좋아요 수를 1 증가시키는 SQL 쿼리
			String sql = "UPDATE friendBoard SET likeCount = likeCount + 1 WHERE num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postNum);

			// 쿼리 실행 및 영향을 받은 행의 수 가져오기
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
	            // 파일이 잠겨 있지 않으면 삭제를 시도합니다.
	            if (file.delete()) {
	                System.out.println("File deleted successfully: " + file.getAbsolutePath());
	                return true;
	            } else {
	                System.out.println("Failed to delete file: " + file.getAbsolutePath());
	                return false;
	            }
	        } else {
	            System.out.println("File is locked: " + file.getAbsolutePath());
	            return false;
	        }
	    } else {
	        System.out.println("File does not exist: " + file.getAbsolutePath());
	        return false;
	    }
	}
}
