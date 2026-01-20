package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.AdminMemberDelete;
import service.AdminMemberList;

@WebServlet("/adm/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminController() {
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
	    
	    // ADMIN 계정이 아니면 메인으로 보냄
	    HttpSession session = request.getSession();
	    if (!"ADMIN".equals(session.getAttribute("role"))) {
	        response.sendRedirect(request.getContextPath() + "/main.do");
	        return;
	    }
	    
	    String uri = request.getPathInfo();
		System.out.println("uri:"+uri);
		
	    String page = null;

	    switch (uri) {
	    	
	    	// 회원 목록 조회
	        case "/admin.do": 
	            new AdminMemberList().doCommand(request, response);
	            page = "/admin/admin.jsp";
	            break;
	            
	        // 회원 삭제 실행
	        case "/forceDeleteMember.do": 
	            new AdminMemberDelete().doCommand(request, response);
	            page = "/admin/admin.jsp";
	            return; 

	        default:
				System.out.println("잘못 된 요청");
				break;
	    }

	    if (page != null) {
	        RequestDispatcher rd = request.getRequestDispatcher(page);
	        rd.forward(request, response);
	    }
		
	}

}
