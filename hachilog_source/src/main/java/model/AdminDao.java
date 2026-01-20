package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class AdminDao {
	
	// 회원 조회
	
	public List<MemberDto> getMemberList() {
		
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "select userid, nickname, email, phone, regdate, role from hmember where role='USER' order by regdate desc";
        
        List<MemberDto> list = new ArrayList<>();
        
        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                MemberDto dto = new MemberDto();
                dto.setUserid(rs.getString("userid"));
                dto.setNickname(rs.getString("nickname"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setRegdate(rs.getString("regdate"));
                dto.setRole(rs.getString("role"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
        return list;
    }

    // 회원 강제 탈퇴
	
    public void deleteMember(String userid) {
    	
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        String sql = "DELETE FROM hmember WHERE userid = ?";
        
        try {
            conn = DBManager.getInstance();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        }
    }

}
