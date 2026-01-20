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

public class InfoEdit implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");

	    HttpSession session = request.getSession();
	    String userid = (String) session.getAttribute("userid");

	    String nickname = request.getParameter("nickname");
	    String email = request.getParameter("email");
	    String phone = request.getParameter("phone");

	    // 기존 비밀번호
	    String password = request.getParameter("password");
	    String pw2 = request.getParameter("pw2");

	    // 새 비밀번호
	    String newPassword = request.getParameter("newPassword");
	    String newPw2 = request.getParameter("newPw2");

	    MemberDao dao = new MemberDao();

	    //  null + 빈값 방어
	    if (password == null || pw2 == null || password.isEmpty() || pw2.isEmpty()) {
	        response.sendRedirect("/mem/infoedit.do?error=pw");
	        return;
	    }

	    // 기존 비밀번호 2번 입력 확인
	    if (!password.equals(pw2)) {
	        response.sendRedirect("/mem/infoedit.do?error=pw");
	        return;
	    }

	    // DB 비밀번호 비교
	    String dbPassword = dao.getPassword(userid);
	    if (!PasswordBcrypt.checkPassword(password, dbPassword)) {
	        response.sendRedirect("/mem/infoedit.do?error=pw");
	        return;
	    }

	    MemberDto dto = new MemberDto();
	    dto.setUserid(userid);
	    dto.setNickname(nickname);
	    dto.setEmail(email);
	    dto.setPhone(phone);

	    // 🔀 비밀번호 변경 여부
	    if (newPassword != null && !newPassword.isEmpty()) {

	        if (newPw2 == null || !newPassword.equals(newPw2)) {
	            response.sendRedirect("/mem/infoedit.do?error=newpw");
	            return;
	        }

	        dto.setPassword(PasswordBcrypt.hashPassword(newPassword));
	        dao.memberEditWithPassword(dto);

	    } else {
	        dao.memberEditWithoutPassword(dto);
	    }

	    response.sendRedirect("/mem/mypage.do");
	}



}
