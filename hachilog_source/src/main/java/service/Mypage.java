package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LikeDao;
import model.LikelistDto;
import model.MemberDao;
import model.MemberDto;
import util.Command;

public class Mypage implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		// 세션에서 userid 가져와서 회원정보 조회
		HttpSession session = request.getSession(); 
		String userid=(String) session.getAttribute("userid");
		
		MemberDao dao=new MemberDao();
		MemberDto dto=dao.memberInfo(userid);
		
		// 좋아요 누른 글 목록 가져오기
        LikeDao lDao = new LikeDao();
        List<LikelistDto> likeList = lDao.likeList(userid); 
        
        request.setAttribute("likeList", likeList);
		request.setAttribute("member", dto);

	}

}
