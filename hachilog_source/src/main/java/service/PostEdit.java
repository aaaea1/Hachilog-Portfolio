package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.PostDao;
import model.PostDto;
import util.Command;

public class PostEdit implements Command {

	@Override
	public void doCommand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int p_no=Integer.parseInt(request.getParameter("p_no"));
		// 기존 이미지
		String old_imgfile = request.getParameter("old_imgfile");
		// 세션에서 userid 가져오기
		HttpSession session = request.getSession(); 
		String userid=(String) session.getAttribute("userid");
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		// 첨부파일 요청 : getPart() (getParameter()로 요청 할 수 없음)
		Part imgfile = request.getPart("imgfile");
		String filename = null;

		PostDto dto = new PostDto();
		
		dto.setP_no(p_no);
		dto.setUserid(userid);
		dto.setTitle(title);
		dto.setCategory(category);
		dto.setContent(content);

		PostDao dao = new PostDao();

		if (imgfile != null && imgfile.getSize() > 0) { // 첨부파일이 존재하면

			// 오리지널 파일 이름을 구한다
			String oriFileName = Paths.get(imgfile.getSubmittedFileName()).getFileName().toString();
			// imgfile.getSubmittedFileName() : c:/사진/a.jpg

			// 파일 중복을 피하기 위해서 오리지널 파일 이름으로 변경 : UUID
			filename = UUID.randomUUID().toString() + "_" + oriFileName;

			System.out.println("UUID:" + filename);

			// 첨부파일을 물리적으로 저장할 경로
			String uploadPath = "C:/postupload";

			// uploadPath 파일 경로 정보 객체 생성
			File uploadDir = new File(uploadPath);

			if (!uploadDir.exists()) { // upload 폴더가 존재하지 않으면

				// 최상위 폴더부터 하위폴더를 생성한다.
				uploadDir.mkdirs();

			}

			// 파일 전체 경로를 구한다.
			String filePath = uploadPath + File.separator + filename;
			// separator 운영체제에 따라 파일 경로를 자동으로 바꾼다.

			System.out.println("filePath:" + filePath);
			imgfile.write(filePath); // 파일을 물리적으로 저장 : .write

			System.out.println("파일 저장 완료:" + filePath);

			dto.setImgfile(filename);

		} else {
			dto.setImgfile(old_imgfile);
		}

		dao.postUpdate(dto);
		response.sendRedirect("/pst/list.do");

	}

}
