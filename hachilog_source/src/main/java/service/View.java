package service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommentDto;
import model.LikeDao;
import model.PostDao;
import model.PostDto;
import util.Command;

public class View implements Command {

    @Override
    public void doCommand(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int p_no = Integer.parseInt(request.getParameter("p_no"));

        PostDao dao = new PostDao();
        dao.viewCount(p_no);
        PostDto dto = dao.contentView(p_no);
        List<CommentDto> list = dao.commentList(p_no);
        
        LikeDao ldao = new LikeDao();
        
        int likeCount = ldao.likeCount(p_no); 
        dto.setLikeCount(likeCount);
        
        // 현재 로그인한 사용자가 이 글을 찜했는지 확인
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
        
        boolean isLiked = false;
        if (userid != null) {
            isLiked = ldao.checkLike(p_no, userid);
        }

        request.setAttribute("dto", dto); // 글 내용
        request.setAttribute("list", list); // 댓글 목록
        request.setAttribute("isLiked", isLiked); // 찜 여부
    }
}
