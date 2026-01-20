package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBManager;

public class MemberDao {
	
	// 회원 가입
	public void joinMember(MemberDto member) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into hmember (userid,nickname,password,email,phone,regdate) values (?, ?, ?, ?, ?, sysdate)";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getNickname());
			pstmt.setString(3, member.getPassword());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 아이디 중복 체크
	public int useridCheck(String userid) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select *from hmember where userid=?";

		int result = 0;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 1; // 아이디 있음
			} else {
				result = -1; // 아이디 없음
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	// 닉네임 중복 체크
	public int nicknameCheck(String nickname) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select *from hmember where nickname=?";

		int result = 0;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, nickname);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 1; // 아이디 있음
			} else {
				result = -1; // 아이디 없음
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	// 로그인
	
	public MemberDto login(String userid) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from hmember where userid=?";
		
		MemberDto dto = null;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDto();
				dto.setUserid(rs.getString("userid"));
				dto.setPassword(rs.getString("password"));
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
				dto.setPhone(rs.getString("phone"));
				dto.setRole(rs.getString("role"));
				dto.setRegdate(rs.getString("regdate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	
	}
	
	// 마이페이지 정보 가져오기 -> 로그인과 거의 비슷하지만 보안상 분리(비밀번호 x!!)
	
	public MemberDto memberInfo(String userid) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from hmember where userid=?";
		
		MemberDto dto = null;
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDto();
				dto.setUserid(rs.getString("userid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setEmail(rs.getString("email"));
				dto.setPhone(rs.getString("phone"));
				dto.setRegdate(rs.getString("regdate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	
	}
	
	// 회원정보 수정하기 - 비밀번호를 변경하지않는 경우
	
	public void memberEditWithoutPassword(MemberDto dto) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update hmember set nickname=?, email=?, phone=? where userid=?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getNickname());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getUserid());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 회원정보 수정하기 - 비밀번호를 변경하는 경우
	
	public void memberEditWithPassword(MemberDto dto) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update hmember set nickname=?, email=?, phone=?, password=? where userid=?";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getNickname());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getPhone());
			pstmt.setString(4, dto.getPassword());
			pstmt.setString(5, dto.getUserid());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// 다른 정보를 수정할때 비밀번호 확인용 - 기존 비밀번호 가져오기
	
	public String getPassword(String userid) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String sql = "select password from hmember where userid=?";
	    String password = null;

	    try {
	        conn = DBManager.getInstance();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userid);

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            password = rs.getString("password");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return password;
	}
	
}
