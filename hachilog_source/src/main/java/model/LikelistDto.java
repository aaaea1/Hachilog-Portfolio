package model;

public class LikelistDto {
	
	// 좋아요 누른 글의 목록을 저장할 DTO
	
	private int l_no;      // 좋아요 번호
    private int p_no;      // 게시글 번호
    private String title;   // 게시글 제목 (post 테이블에서 가져옴)
    private String nickname;  // 작성자 (post 테이블에서 가져옴)
    private String regdate; // 작성일 (post 테이블에서 가져옴)
    private String imgfile; // 이미지 파일 (post 테이블에서 가져옴)
    private int likeCount; // 좋아요 수
    
	public int getL_no() {
		return l_no;
	}
	public void setL_no(int l_no) {
		this.l_no = l_no;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getImgfile() {
		return imgfile;
	}
	public void setImgfile(String imgfile) {
		this.imgfile = imgfile;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
    
    

}
