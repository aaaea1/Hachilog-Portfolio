package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDao;
import util.Command;

public class NicknameCheck implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nickname=request.getParameter("nickname");
		System.out.println("ajax로 요청된 닉네임:"+nickname);
		MemberDao dao=new MemberDao();
		int result=dao.nicknameCheck(nickname);
		
		// 클라이언트로 결과 전송
		response.getWriter().print(result);
	}

}
