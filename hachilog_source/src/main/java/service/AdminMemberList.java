package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AdminDao;
import model.MemberDto;
import util.Command;

public class AdminMemberList implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		List<MemberDto> members = new ArrayList<MemberDto>();
		AdminDao dao = new AdminDao();

		members = dao.getMemberList();

		request.setAttribute("members", members);

	}

}
