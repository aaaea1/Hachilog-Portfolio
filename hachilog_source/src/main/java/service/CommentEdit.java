package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommentDto;
import model.PostDao;
import util.Command;

public class CommentEdit implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int c_no=Integer.parseInt(request.getParameter("c_no"));
		String content=request.getParameter("content");
		
		PostDao dao=new PostDao();
		CommentDto dto=new CommentDto();
		
		dto.setC_no(c_no);
		dto.setContent(content);
		
		dao.commentUpdate(dto);
		
	}

}
