package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDao;
import model.MemberDto;
import util.Command;
import util.PasswordBcrypt;

public class Login implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 로그인
		request.setCharacterEncoding("UTF-8");
		
		String userid=request.getParameter("userid");
		String password=request.getParameter("password");
		
		MemberDao dao=new MemberDao();
		MemberDto dto=dao.login(userid);
		
		if(dto!=null&&PasswordBcrypt.checkPassword(password,dto.getPassword())) {
			
			// 세션 생성
			HttpSession session=request.getSession();
			
			// userid를 userid 세션 속성에 저장
			session.setAttribute("userid", dto.getUserid());
			session.setAttribute("role", dto.getRole());
			
			response.getWriter().print("success"); // ajax로 success 를 보냄
		}

	}

}
