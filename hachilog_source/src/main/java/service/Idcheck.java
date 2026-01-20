package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDao;
import util.Command;

public class Idcheck implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userid=request.getParameter("userid");
		System.out.println("ajax로 요청된 아이디:"+userid);
		MemberDao dao=new MemberDao();
		int result=dao.useridCheck(userid);
		
		// 클라이언트로 결과 전송
		response.getWriter().print(result);
	}
}
