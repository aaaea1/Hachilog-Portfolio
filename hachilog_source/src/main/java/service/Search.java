package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDao;
import model.PostDto;
import util.Command;

public class Search implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 1. 파라미터 받기
		String keyword = request.getParameter("keyword");
		String category = request.getParameter("category");
		String pageNum = request.getParameter("page");
		if (pageNum == null)
			pageNum = "1";

		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 5;
		int pageBlock = 5;

		// 2. 현재 페이지에서 보여줄 첫 페이지와 끝 페이지 계산
		int start = (currentPage - 1) * pageSize + 1;
		int end = currentPage * pageSize;

		PostDao dao = new PostDao();
		List<PostDto> list;
		int totalCount = 0;

		// 3. 조건별 데이터 및 개수 가져오기
		if (category != null && !category.trim().isEmpty()) {
			// 카테고리가 있을 때 (페이징 포함)
			list = dao.category(category, start, end);
			totalCount = dao.getCategoryCount(category);
		} else if (keyword != null && !keyword.trim().isEmpty()) {
			// 검색어가 있을 때 (페이징 포함)
			String upperKeyword = keyword.toUpperCase();
			list = dao.searchList(upperKeyword, start, end);
			totalCount = dao.getSearchCount(upperKeyword);
		} else {
			// 둘 다 없을 때 (전체 목록 페이징)
			list = dao.list(start, end);
			totalCount = dao.getCount();
		}

		// 4. 하단 페이징 숫자 계산 (ListAll과 동일한 산수)
		int totalPage = (int) Math.ceil((double) totalCount / pageSize);
		int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		if (endPage > totalPage)
			endPage = totalPage;

		// 5. 결과 전달
		request.setAttribute("list", list);
		request.setAttribute("keyword", keyword);
		request.setAttribute("category", category);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);

		request.getRequestDispatcher("/post/searchList.jsp").forward(request, response);

	}

}
