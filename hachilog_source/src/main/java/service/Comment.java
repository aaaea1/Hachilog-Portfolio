package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommentDto;
import model.PostDao;
import util.Command;

public class Comment implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int p_no=Integer.parseInt(request.getParameter("p_no"));
		String userid=request.getParameter("userid");
		String content=request.getParameter("content");
		
		CommentDto dto=new CommentDto();
		
		dto.setP_no(p_no);
		dto.setUserid(userid);
		dto.setContent(content);
		
		PostDao dao=new PostDao();
		dao.commentInsert(dto);
		
		request.setAttribute("list", dao.commentList(p_no));
		request.getRequestDispatcher("/post/commentList.jsp").forward(request, response);
		
	}

}
