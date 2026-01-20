package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Command;

public class Logout implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession(false);
		// 기존 세션이 있으면 가져오고 (userid) 없으면 null 을 반환
		// false : 새로운 세션을 생성하지 않는다.
		
		if(session!=null) {
			session.invalidate(); // 세션 무효화
		}
		
		response.sendRedirect("/main.do"); // index 로 돌아감

	}

}
