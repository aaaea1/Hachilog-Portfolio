package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class PostDao {

	// 글쓰기

	public void post(PostDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into post (p_no,userid,category,title,content,imgfile) values (post_seq.nextval,?,?,?,?,?)";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUserid());
			pstmt.setString(2, dto.getCategory());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getImgfile());

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

	// 전체 글 가져오기

	public List<PostDto> list(int startRow, int endRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from (" + "  select rownum as rnum, a.* from ("
				+ "    select p.*, m.nickname from post p " + "    join hmember m on p.userid = m.userid "
				+ "    order by p.p_no desc" + "  ) a where rownum <= ?" + ") where rnum >= ?";

		List<PostDto> list = new ArrayList<PostDto>();

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, endRow); // 예: 1페이지면 5, 2페이지면 10
			pstmt.setInt(2, startRow); // 예: 1페이지면 1, 2페이지면 6

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDto dto = new PostDto();
				dto.setP_no(rs.getInt("p_no"));
				dto.setUserid(rs.getString("userid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
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

	// 메인에서 최신글 보여주기

	public List<PostDto> listIndex() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM (SELECT * FROM post ORDER BY p_no DESC) WHERE ROWNUM <= 3";

		List<PostDto> list = new ArrayList<PostDto>();

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDto dto = new PostDto();
				dto.setP_no(rs.getInt("p_no"));
				dto.setUserid(rs.getString("userid"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}

	// 글 상세보기

	public PostDto contentView(int p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT  \r\n"
				+ "    p.*,    \r\n"
				+ "    m.nickname\r\n"
				+ "FROM post p\r\n"
				+ "JOIN hmember m \r\n"
				+ "ON p.userid = m.userid\r\n"
				+ "WHERE p.p_no = ?";
		PostDto dto = new PostDto();

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setP_no(rs.getInt("p_no"));
				dto.setUserid(rs.getString("userid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setCategory(rs.getString("category"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
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
		return dto;
	}

	// 조회수 증가

	public void viewCount(int p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update post set views=views+1 where p_no=?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, p_no);

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

	// 글 수정하기

	public void postUpdate(PostDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = null;

		sql = "update post set category=?,title=?,content=?,imgfile=? where p_no=?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getCategory());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getImgfile());
			pstmt.setInt(5, dto.getP_no());
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

	// 제목이나 작성자(닉네임)로 글 검색하기

	public List<PostDto> searchList(String keyword, int start, int end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM (" + "  SELECT ROWNUM AS rnum, a.* FROM ("
				+ "    SELECT p.*, m.nickname FROM post p " + "    JOIN hmember m ON p.userid = m.userid "
				+ "    WHERE UPPER(p.title) LIKE ? OR UPPER(m.nickname) LIKE ? " + "    ORDER BY p.p_no DESC"
				+ "  ) a WHERE ROWNUM <= ?" + ") WHERE rnum >= ?";

		List<PostDto> list = new ArrayList<PostDto>();

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			pstmt.setInt(3, end);
			pstmt.setInt(4, start);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDto dto = new PostDto();
				dto.setP_no(rs.getInt("p_no"));
				dto.setUserid(rs.getString("userid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
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
	
	// 검색했을때 나오는 글의 갯수

	public int getSearchCount(String keyword) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) FROM post p JOIN hmember m ON p.userid = m.userid "
				+ "WHERE UPPER(p.title) LIKE ? OR UPPER(m.nickname) LIKE ?";

		int count = 0;

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			String key = "%" + keyword.toUpperCase() + "%";

			pstmt.setString(1, key);
			pstmt.setString(2, key);

			rs = pstmt.executeQuery();

			if (rs.next())
				count = rs.getInt(1);
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
		return count;
	}

	// 카테고리로 글 목록 가져오기

	public List<PostDto> category(String category, int start, int end) {
		List<PostDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM (" + "  SELECT ROWNUM AS rnum, a.* FROM ("
				+ "    SELECT p.*, m.nickname FROM post p " + "    JOIN hmember m ON p.userid = m.userid "
				+ "    WHERE p.category = ? " 
				+ "    ORDER BY p.p_no DESC" + "  ) a WHERE ROWNUM <= ?" + ") WHERE rnum >= ?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PostDto dto = new PostDto();
				dto.setP_no(rs.getInt("p_no"));
				dto.setUserid(rs.getString("userid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setImgfile(rs.getString("imgfile"));
				dto.setViews(rs.getInt("views"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 카테고리 필터 걸렀을때 게시물 수

	public int getCategoryCount(String category) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) FROM post WHERE UPPER(TRIM(category)) = UPPER(TRIM(?))";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	// 글 삭제하기

	public void postDelete(int p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from post where p_no=?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, p_no);

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

	// 댓글 작성하기

	public void commentInsert(CommentDto dto) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into hcomment (c_no,p_no,userid,content) values (hcomment_seq.nextval,?,?,?)";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getP_no());
			pstmt.setString(2, dto.getUserid());
			pstmt.setString(3, dto.getContent());

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

	// 댓글 보기

	public List<CommentDto> commentList(int p_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT \r\n" + "    c.*, \r\n" + "    m.nickname \r\n" + "FROM \r\n" + "    hcomment c \r\n"
				+ "JOIN \r\n" + "    hmember m ON c.userid = m.userid \r\n" + "WHERE \r\n" + "    c.p_no = ? \r\n"
				+ "ORDER BY \r\n" + "    c.c_no DESC";

		List<CommentDto> list = new ArrayList<CommentDto>();

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentDto dto = new CommentDto();
				dto.setC_no(rs.getInt("c_no"));
				dto.setP_no(rs.getInt("p_no"));
				dto.setUserid(rs.getString("userid"));
				dto.setNickname(rs.getString("nickname"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate").substring(0, 10));
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

	// 댓글 수정하기

	public void commentUpdate(CommentDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update hcomment set content=? where c_no=?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getC_no());

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

	// 댓글 삭제하기

	public void commentDelete(int c_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from hcomment where c_no=?";

		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, c_no);

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

	// 페이지 계산을 위해서 전체 글 개수를 가져오는 메서드

	public int getCount() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		String sql = "select count(*) as cnt from post";
		
		try {
			conn = DBManager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) { 
		        count = rs.getInt("cnt"); 
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
		return count;
	}

}
