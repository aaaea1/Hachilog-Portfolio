<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<div class="container mypage-container">
	<div class="page-title">会員情報</div>

	<!-- 회원 정보 -->
	<div class="write-form-container mypage-profile">
		<div class="info-grid">
			<div class="member-details">
				<p>
					<strong>ユーザーID:</strong> ${sessionScope.userid}
				</p>
				<p>
					<strong>ニックネーム:</strong> ${member.nickname}
				</p>
				<p>
					<strong>メールアドレス:</strong> ${member.email}
				</p>
				<p>
					<strong>登録日:</strong> ${member.regdate.substring(0,10)}
				</p>
			</div>

			<div class="profile-actions">
				<a href="/mem/infoedit.do" class="btn btn-submit">編集</a>
			</div>
		</div>
	</div>

	<!-- 좋아요한 글 -->

	<h2>気に入り記事</h2>

	<section class="list-section">
		<div class="post-list">
			<c:choose>
				<c:when test="${not empty likeList}">
					<c:forEach var="item" items="${likeList}">
						<div class="post-row">
							<img
								src="/postimg/${not empty item.imgfile ? item.imgfile : 'default.png'}"
								alt="thumb">

							<div class="post-info">
								<a href="/pst/view.do?p_no=${item.p_no}"> ${item.title}</a>
								<div>
									<span class="post-author">${item.nickname}</span> <span
										class="post-date">· ${item.regdate.substring(0,10)}</span>

									<button type="button" class="heart-btn liked"
										onclick="toggleLikeInMypage(${item.p_no}, this)">
										<span class="heart-icon">♥</span> <span class="like-count">${item.likeCount}</span>
									</button>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p style="padding: 20px; color: #888; text-align: center;">気に入り記事がありません。</p>
				</c:otherwise>
			</c:choose>
		</div>
	</section>
</div>




<script>
function toggleLikeInMypage(pNo, btn) {
    const userid = "${sessionScope.userid}";
    if (!userid) {
        alert("ログインが必要です。");
        return;
    }

    const icon = btn.querySelector('.heart-icon');
    const countEl = btn.querySelector('.like-count');

    // 서버로 좋아요 토글 요청 전송
    $.post("/pst/like.do", { p_no: pNo, userid: userid })
     .done(function(data) {
         // data 형식: "n|liked" 또는 "n|unliked"
         const result = data.split("|");
         const count = result[0];
         const status = result[1];

         // 숫자 업데이트
         countEl.textContent = count;

         // 상태에 따른 UI 변경
         if (status === "liked") {
             icon.textContent = '♥';
             btn.classList.add('liked');
         } else {
             icon.textContent = '♡';
             btn.classList.remove('liked');
             
             // 1. 취소 시 리스트에서 바로 사라지게 처리
             $(btn).closest('.post-row').fadeOut(300, function() {
                 // 2. fadeOut 애니메이션이 완전히 끝난 후 실행되는 콜백 함수
                 $(this).remove(); // 메모리에서 완전히 삭제
                 
                 // 3. 남은 .post-row의 개수를 체크
                 if ($('.post-row').length === 0) {
                     // 4. 개수가 0이면 HTML을 강제로 교체
                     const emptyHtml = '<p style="padding: 20px; color: #888; text-align: center;">気に入り記事がありません。</p>';
                     $('.post-list').html(emptyHtml);
                 }
             });
         }
     })
     .fail(function() {
         alert("失敗しました。");
     });
}
</script>


<%@ include file="../footer.jsp"%>
