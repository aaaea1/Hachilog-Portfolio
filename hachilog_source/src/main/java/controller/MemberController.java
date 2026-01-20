package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Idcheck;
import service.InfoEdit;
import service.Join;
import service.Login;
import service.Logout;
import service.Mypage;
import service.NicknameCheck;

@WebServlet("/mem/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String uri=request.getPathInfo();
		System.out.println("uri:"+uri);
		
		String page=null;
		
		switch (uri) {
		
		// 회원가입 화면 이동
		case "/join.do":
			page="/member/join.jsp";
			break;
			
		// 아이디 중복체크
		case "/idcheck.do":
			new Idcheck().doCommand(request, response);
			break;
		
		// 닉네임 중복체크
		case "/nicknamecheck.do":
			new NicknameCheck().doCommand(request, response);
			break;
		
		// 회원가입 처리
		case "/membersave.do":
			new Join().doCommand(request, response);
			break;
			
		// 로그인 화면 이동
		case "/login.do":
			page="/member/login.jsp";
			break;
			
		// 로그인 실행
		case "/loginaction.do":
			new Login().doCommand(request, response);
			break;
			
		// 마이페이지 이동
		case "/mypage.do":
			new Mypage().doCommand(request, response);
			page="/member/mypage.jsp";
			break;
			
		// 로그아웃 실행
		case "/logout.do":
			new Logout().doCommand(request, response);
			break;
			
		// 회원정보 수정 페이지 이동
		case "/infoedit.do":
			new Mypage().doCommand(request, response);
			page="/member/infoEdit.jsp";
			break;
		
		// 회원정보 수정 실행
		case "/infoeditaction.do":
			new InfoEdit().doCommand(request, response);
			break;
			
		default:
			System.out.println("잘못 된 요청");
			break;
			
		}
		
		if(page!=null) {
			RequestDispatcher rd=request.getRequestDispatcher(page);
			rd.forward(request, response);
		}
	}

}
