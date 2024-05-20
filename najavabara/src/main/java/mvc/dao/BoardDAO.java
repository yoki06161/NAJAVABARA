package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mvc.common.JDBCConnect;
import mvc.dto.BoardDTO;

public class BoardDAO {

    public List<BoardDTO> selectList(Map<String, String> map) {
        List<BoardDTO> boardLists = new ArrayList<>();
        String searchField = map.get("searchField");
        String searchWord = map.get("searchWord");

        String sql = "SELECT * FROM accidentBoard";
        if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
            sql += " WHERE " + searchField + " LIKE ?";
        }

        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
                pstmt.setString(1, "%" + searchWord + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BoardDTO dto = new BoardDTO();
                dto.setNum(rs.getInt("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getTimestamp("postdate"));
                dto.setVisitcount(rs.getInt("visitcount"));
                dto.setId(rs.getString("id"));
                boardLists.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardLists;
    }

    public BoardDTO selectById(int num) {
        BoardDTO dto = null;
        String sql = "SELECT * FROM accidentBoard WHERE num = ?";

        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, num);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                dto = new BoardDTO(
                    rs.getInt("num"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("id")
                );
                dto.setPostdate(rs.getTimestamp("postdate"));
                dto.setVisitcount(rs.getInt("visitcount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public int selectCount(Map<String, String> map) {
        int totalCount = 0;
        String searchField = map.get("searchField");
        String searchWord = map.get("searchWord");

        String sql = "SELECT COUNT(*) FROM accidentBoard";
        if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
            sql += " WHERE " + searchField + " LIKE ?";
        }

        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
                pstmt.setString(1, "%" + searchWord + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    public void insertPost(BoardDTO dto) {
        String sql = "INSERT INTO accidentBoard (title, content, id, postdate, visitcount) VALUES (?, ?, ?, NOW(), 0)";

        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePost(BoardDTO dto) {
        String sql = "UPDATE accidentBoard SET title = ?, content = ?, id = ? WHERE num = ?";

        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setString(3, dto.getId());
            pstmt.setInt(4, dto.getNum());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 조회수증가
    public int updateVisitCount(BoardDTO dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            // 데이터베이스 연결
            conn = JDBCConnect.getConnection();
            
            // SQL 쿼리 작성
            String sql = "UPDATE accidentBoard SET visitcount = visitcount + 1 WHERE num = ?";
            pstmt = conn.prepareStatement(sql);
            
            // 쿼리 파라미터 설정
            pstmt.setInt(1, dto.getNum());
            
            // 쿼리 실행
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            JDBCConnect.close(pstmt, conn);
        }
        return result;
    }
}