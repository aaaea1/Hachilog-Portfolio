package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDao;
import model.PostDto;
import util.Command;

public class ListAll implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		// 1. 설정
	    int pageSize = 5;    // 한 페이지에 보여줄 게시글 수
	    int pageBlock = 5;   // 하단에 보여줄 페이지 번호 개수

	    // 2. 현재 페이지 파라미터 받기
	    String pageNum = request.getParameter("page");
	    if (pageNum == null) pageNum = "1";
	    int currentPage = Integer.parseInt(pageNum);

	    // 3. DB 범위 계산 (pageSize가 5일 때)
	    // 1페이지: 1~5, 2페이지: 6~10, 3페이지: 11~15
	    int start = (currentPage - 1) * pageSize + 1;
	    int end = currentPage * pageSize;

	    // 4. 데이터 가져오기
	    PostDao dao = new PostDao();
	    List<PostDto> list = dao.list(start, end); // 5개만 가져옴
	    int totalCount = dao.getCount();                 // 총 글 개수

	    // 5. 하단 페이징 숫자 계산
	    // 전체 페이지 수 (예: 글이 13개면 13/5 = 2.6 -> 올림해서 3페이지)
	    int totalPage = (int)Math.ceil((double)totalCount / pageSize);
	    
	    // 시작 페이지 번호 (지금 1~5페이지 중 어디를 눌러도 시작은 1)
	    int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
	    
	    // 끝 페이지 번호
	    int endPage = startPage + pageBlock - 1;
	    if (endPage > totalPage) endPage = totalPage; // 실제 끝 번호로 보정

	    // 6. JSP로 결과 전달
	    request.setAttribute("list", list);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("startPage", startPage);
	    request.setAttribute("endPage", endPage);
	    request.setAttribute("totalPage", totalPage);

	}

}
