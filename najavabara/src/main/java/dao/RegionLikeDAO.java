package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.JDBCConnect;
import dto.RegionLikeDTO;

public class RegionLikeDAO {
	// 좋아요 클릭 시 이미 좋아요를 눌렀는지 확인
	public RegionLikeDTO hasUserLiked(RegionLikeDTO ldto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// select문에서만 사용

		try {
			// connection
			conn = JDBCConnect.getConnection();

			// sql 창- 리스트면 where ..=?부분 수정
			String sql = "select * from regionLike where id = ? and num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ldto.getId());
			pstmt.setInt(2, ldto.getNum());
			// execute
			rs = pstmt.executeQuery(); 
			
			// 있는지 판단  
			if (rs.next()) { // id와 num 존재
				String id = rs.getString("id");
				int num = rs.getInt("num");
				// 생성자 필요에 따라 추가
				ldto = new RegionLikeDTO(id, num);
				//System.out.println(id +"," + num + ", " + ldto);
			} else {
	            // 해당 레코드가 없는 경우
	            ldto = null;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return ldto;
	}

	// 좋아요 클릭 시 좋아요 테이블에 데이터 삽입
	public void insertLike(RegionLikeDTO ldto) {
		Connection conn = null;
		PreparedStatement pstmt = null;  

		try {
			// conn
			conn = JDBCConnect.getConnection();

			// sql + 쿼리창
			String sql = "insert into regionLike(id, num) values(?, ?)";
			pstmt = conn.prepareStatement(sql);

			// ?에 들어갈 컬럼들 세팅
			pstmt.setString(1, ldto.getId());
			pstmt.setInt(2, ldto.getNum());

			// execute 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
	}
}
