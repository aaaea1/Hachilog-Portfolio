package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDao;
import util.Command;

public class CommentDelete implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int c_no = Integer.parseInt(request.getParameter("c_no"));
		
		PostDao dao = new PostDao();
		
		dao.commentDelete(c_no);

	}

}
