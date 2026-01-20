package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.PostEdit;
import service.Search;
import service.Comment;
import service.CommentDelete;
import service.CommentEdit;
import service.Like;
import service.ListAll;
import service.Post;
import service.PostDelete;
import service.View;


@WebServlet("/pst/*")

@MultipartConfig( 
	fileSizeThreshold = 1024*1024*2, // 2MB 메모리 또는 임시파일에 잠깐 저장
	maxFileSize = 1024*1024*10, // 파일 1개 당 최대 크기 10MB
	maxRequestSize = 1024*1024*50 // 폼 전체 크기(파일+텍스트) 50MB 까지 허용 
)
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostController() {
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
		
		// 글 목록 이동
		case "/list.do":
			new ListAll().doCommand(request, response);
			page="/post/list.jsp";
			break;
			
		// 글 쓰기 이동
		case "/write.do":
			page="/post/write.jsp";
			break;
			
		// 글 쓰기 실행
		case "/post.do":
			new Post().doCommand(request, response);
			break;
			
		// 글 상세보기
		case "/view.do":
			new View().doCommand(request, response);
			page = "/post/view.jsp";
			break;
			
		// 글 수정 이동
		case "/edit.do":
			new View().doCommand(request, response);
			page="/post/edit.jsp";
			break;
		
		// 글 수정 실행
		case "/editaction.do":
			new PostEdit().doCommand(request, response);
			System.out.println("uri:"+uri);
			break;
			
		// 글 검색하기
		case "/searchList.do":
			new Search().doCommand(request, response);
			break;
			
		// 글 삭제
		case "/delete.do":
			new PostDelete().doCommand(request, response);
			break;
		
		// 댓글 작성
		case "/comment.do":
			new Comment().doCommand(request, response);
			break;
			
		// 댓글 수정
		case "/commentEdit.do":
			new CommentEdit().doCommand(request, response);
			break;
			
		// 댓글 삭제
		case "/commentDelete.do":
			new CommentDelete().doCommand(request, response);
			break;
			
		// 좋아요
		case "/like.do":
			new Like().doCommand(request, response);
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