package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.LikeDao;
import util.Command;

public class Like implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int p_no = Integer.parseInt(request.getParameter("p_no"));
		String userid = request.getParameter("userid");

		LikeDao dao = new LikeDao();

		// 1. 좋아요 상태 확인 및 토글 (있으면 삭제, 없으면 삽입)
		boolean isLiked = dao.checkLike(p_no, userid);
		
		if (isLiked) {
			dao.deleteLike(userid, p_no);
		} else {
			dao.postLike(userid, p_no);
		}

		// 2. 최신 좋아요 총 개수 가져오기
		int count = dao.likeCount(p_no);
		String status = isLiked ? "unliked" : "liked";

		// 3. AJAX 응답 전송 (형식: "개수|상태")
		response.getWriter().print(count + "|" + status);

	}

}
