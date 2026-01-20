<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>

<div class="container">
	<div class="content-wrap">

		<div class="post-view">

			<div class="post-header">
				<div>
					<h2 class="post-title">${dto.title}</h2>
					<div class="post-meta">
						<div class="meta-item">
							<span>${dto.nickname}</span>
						</div>
						<div class="meta-item">
							<span>${dto.regdate}</span>
						</div>
						<div class="meta-item">
							<span>${dto.views}ビュー</span>
						</div>
					</div>
				</div>
				<div style="text-align: right">
					<button id="likeBtn" class="heart-btn ${isLiked ? 'liked' : ''}">
						<span id="likeIcon">${isLiked ? '❤' : '♡'}</span> <span
							id="likeCount">${dto.likeCount}</span>
					</button>

					<div style="margin-top: 12px">
						<a href="/pst/list.do?page=${empty param.page ? 1 : param.page}"
							style="color: #888; text-decoration: none;">一覧</a>

						<c:if test="${sessionScope.userid == dto.userid}">
							<button type="button" onclick="postEdit(${dto.p_no})"
								style="background: none; border: none; color: #888; text-decoration: none; margin-left: 12px; cursor: pointer;">編集</button>
						</c:if>

						<c:if
							test="${sessionScope.userid == dto.userid || sessionScope.role == 'ADMIN'}">
							<button type="button" onclick="postDelete(${dto.p_no})"
								style="background: none; border: none; color: #888; text-decoration: none; margin-left: 12px; cursor: pointer;">
								<c:choose>
									<c:when
										test="${sessionScope.role == 'ADMIN' && sessionScope.userid != dto.userid}">
										<span style="color: #ff4d4d;">削除</span>
									</c:when>
									<c:otherwise>削除</c:otherwise>
								</c:choose>
							</button>
						</c:if>
					</div>
				</div>
			</div>

			<img src="/postimg/${dto.imgfile}" alt="thumb">

			<article class="post-body">${dto.content}</article>

			<!-- 댓글 작성 -->

			<section class="comments">
				<div class="comment-form">
					<h3>コメント</h3>
					<form id="commentForm">
						<input type="hidden" id="userid" name="userid"
							value="${sessionScope.userid}"> <input type="hidden"
							id="p_no" name="p_no" value="${dto.p_no}">
						<textarea id="commentText" placeholder="コメントを入力してください。"></textarea>
						<div class="form-actions"
							style="display: flex; justify-content: flex-end; gap: 10px; margin-top: 10px;">
							<button type="button" id="commentSubmit" class="btn btn-submit">コメントする</button>
						</div>
					</form>
				</div>
			</section>

			<div id="commentList" class="comment-list">
				<%@ include file="commentList.jsp"%>
			</div>

		</div>
	</div>
</div>

<script>

	// 게시물 삭제
	
	function postDelete(p_no) {
		if(confirm("削除しますか？")) {
			location.href="/pst/delete.do?p_no="+p_no;
		}
	}
	
	// 게시물 수정
	
	function postEdit(p_no) {
		if(confirm("編集しますか？")) {
			 location.href="/pst/edit.do?p_no="+p_no;
		}
	}
	
	// 댓글 기능
	
$(document).ready(function() {
    $("#commentSubmit").click(function() {
        const userid = $("#userid").val();
        const p_no = $("#p_no").val();
        const content = $("#commentText").val().trim();

        if (!userid) { alert("ログインが必要です。"); return; }
        if (!content) { alert("内容を入力してください。"); return; }

        $.post("/pst/comment.do", { userid: userid, p_no: p_no, content: content })
         .done(function(data) {
             $("#commentText").val("");           // 입력 초기화
             $("#commentList").html(data);        // 전체 댓글 갱신
         })
         .fail(function() { alert("コメント登録失敗"); });
    });
});


$(document).ready(function() {

	$("#commentList").on("click", ".btn-edit", function() {
	    const $comment = $(this).closest(".comment-item");
	    const $content = $comment.find(".comment-content");

	    if ($comment.hasClass("editing")) return; // 이미 수정 중이면 무시

	    // 기존 댓글 내용 가져오기
	    // HTML <br> -> 줄바꿈, &nbsp; -> 공백 처리
	    let original = $content.html()
	                           .replace(/<br\s*\/?>/gi, '\n')
	                           .replace(/&nbsp;/g, ' ')
	                           .trim();

	    $comment.addClass("editing");

	    // textarea 생성 후 값 세팅 (.val 사용)
	    const $textarea = $('<textarea class="edit-text" style="width:100%;height:80px;"></textarea>');
	    $textarea.val(original); // 여기서 기존 댓글 내용 그대로 들어감

	    // 기존 댓글 영역 초기화 후 textarea와 버튼 추가
	    $content.empty().append($textarea)
	            .append(`<div class="edit-actions" style="margin-top:5px;">
	                        <button class="edit-save">保存</button>
	                        <button class="edit-cancel">キャンセル</button>
	                     </div>`);

	    // 원본 데이터 저장 (취소 시 필요)
	    $textarea.data("original", original);
	});

    // 수정 저장
    $("#commentList").on("click", ".edit-save", function() {
        const $comment = $(this).closest(".comment-item");
        const $textarea = $comment.find(".edit-text");
        const newContent = $textarea.val().trim();
        const c_no = $comment.data("id");

        if (!newContent) { alert("内容を入力してください"); return; }

        $.post("/pst/commentEdit.do", { c_no: c_no, content: newContent })
         .done(function() {
             $comment.find(".comment-content").text(newContent);
             $comment.removeClass("editing");
         })
         .fail(function() { alert("更新失敗"); });
    });

    // 수정 취소
    $("#commentList").on("click", ".edit-cancel", function() {
        const $comment = $(this).closest(".comment-item");
        const $textarea = $comment.find(".edit-text");
        const original = $textarea.data("original");
        $comment.find(".comment-content").text(original);
        $comment.removeClass("editing");
    });

    // 댓글 삭제 
    $("#commentList").on("click", ".btn-delete", function() {
        const $comment = $(this).closest(".comment-item");
        const c_no = $comment.data("id");
        if (!confirm("削除しますか？")) return;

        $.post("/pst/commentDelete.do", { c_no: c_no })
         .done(function() { $comment.remove(); })
         .fail(function() { alert("削除失敗"); });
    });

});

$(document).ready(function() {
    $("#likeBtn").click(function() {
        const p_no = $("#p_no").val(); 
        const userid = $("#userid").val();

        if (!userid) {
            alert("ログインが必要です。");
            return;
        }

        $.post("/pst/like.do", { p_no: p_no, userid: userid })
         .done(function(data) {
             const result = data.split("|"); 
             $("#likeCount").text(result[0]); 
             
             if(result[1] === "liked") {
                 $("#likeIcon").text("❤");
                 $("#likeBtn").addClass("liked");
             } else {
                 $("#likeIcon").text("♡");
                 $("#likeBtn").removeClass("liked");
             }
         })
         .fail(function() { alert("失敗"); });
    });
});
	</script>

<%@include file="../footer.jsp"%>