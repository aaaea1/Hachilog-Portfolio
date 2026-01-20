package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDao;
import model.PostDto;
import util.Command;

public class ListIndex implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		List<PostDto> list=new ArrayList<PostDto>();
		PostDao dao=new PostDao();
		list=dao.listIndex();
		
		request.setAttribute("list", list);

	}

}
