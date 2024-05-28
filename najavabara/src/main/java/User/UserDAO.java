package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO {
	
	// 유저 리스트 메소드
	public static List<UserDTO> name() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// dto를 리스트화?
		List<UserDTO> userlist = new ArrayList();
		
		try {
			conn = Conn.getConn();
			
			String sql = "select * from user";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// db에서 값 불러온 후 변수에 할당
			while (rs.next()) {
				int idx = rs.getInt("idx");
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String role = rs.getString("role");
				String area = rs.getString("area");
				Date regdate = rs.getDate("regdate");
				
				// dto에 db값 할당(위의 변수값)
				UserDTO dto = new UserDTO(idx, id, pw, name, role, area, regdate);
				userlist.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conn.allClose(rs, pstmt, conn);
		}
		return userlist;
	}
}
