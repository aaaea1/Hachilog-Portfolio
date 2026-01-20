package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class LikeDao {

	// 좋아요 등록
	
	public int postLike(String userid, int p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into hlike (l_no,p_no,userid) values (hlike_seq.nextval,?,?)";

		int result = 0;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, p_no);
			pstmt.setString(2, userid);

			result = pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			// DB의 무결성 제약조건을 위반 했을 때 발생하는 예외
			e.printStackTrace();
			result = -1;
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
		return result;
	}

	// 특정 유저가 좋아요를 누른글인지 확인하는 메서드

	public boolean checkLike(int p_no, String userid) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select count(*) from hlike where p_no = ? and userid = ?";

		boolean result = false;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_no);
			pstmt.setString(2, userid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				// 좋아요 누름 = 1
				if (rs.getInt(1) > 0)
					result = true;
			}
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
		return result;
	}

	// 찜 취소

	public int deleteLike(String userid, int p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from hlike where userid=? and p_no=?";

		int result = 0;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userid);
			pstmt.setInt(2, p_no);

			result = pstmt.executeUpdate();

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
		return result;
	}

	// 찜한 글 목록 조회

	public List<LikelistDto> likeList(String userid) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select \r\n"
				+ "    l.l_no, \r\n"
				+ "    p.p_no, \r\n"
				+ "    p.title, \r\n"
				+ "    p.regdate, \r\n"
				+ "    p.imgfile, \r\n"
				+ "    m.nickname,\r\n"
				+ "    (select count(*) from hlike where p_no = p.p_no) as cnt\r\n"
				+ "from hlike l\r\n"
				+ "join post p on l.p_no = p.p_no\r\n"
				+ "join hmember m on p.userid = m.userid \r\n"
				+ "where l.userid = ? \r\n"
				+ "order by l.l_no desc";

		List<LikelistDto> list = new ArrayList<LikelistDto>();

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userid);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				LikelistDto dto = new LikelistDto();
				dto.setL_no(rs.getInt("l_no"));
				dto.setP_no(rs.getInt("p_no"));
				dto.setTitle(rs.getString("title"));
				dto.setNickname(rs.getString("nickname"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setLikeCount(rs.getInt("cnt")); // 좋아요 수 설정

				list.add(dto);
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return list;

	}
	
	// 찜 갯수 구하기
	
	public int likeCount(int p_no) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    String sql = "select count(*) as cnt from hlike where p_no = ?";
	    
	    int cnt = 0;
	    
	    try {
	        conn =DBManager.getInstance();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, p_no);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            cnt = rs.getInt("cnt"); 
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    }
	    return cnt;
	}

}
