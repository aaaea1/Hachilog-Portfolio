package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDao;
import model.MemberDto;
import util.Command;
import util.PasswordBcrypt;

public class Join implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String userid = request.getParameter("userid");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		MemberDto dto = new MemberDto();
		
		dto.setUserid(userid);
		dto.setNickname(nickname);
		String hashpassword=PasswordBcrypt.hashPassword(password);
		dto.setPassword(hashpassword);
		dto.setEmail(email);
		dto.setPhone(phone);
		
		MemberDao dao = new MemberDao();
		dao.joinMember(dto);
		
		// 회원가입이 완료되면 메인 페이지로 이동
		response.sendRedirect("/main.do"); 
		

	}

}
