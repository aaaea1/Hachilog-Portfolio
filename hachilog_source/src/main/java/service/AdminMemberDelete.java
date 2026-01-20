package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AdminDao;
import util.Command;

public class AdminMemberDelete implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String userid = request.getParameter("userid");

		AdminDao dao = new AdminDao();
		dao.deleteMember(userid);
		
		response.sendRedirect("/adm/admin.do");
		System.out.println("삭제된 사용자: " + userid);
		

	}

}
