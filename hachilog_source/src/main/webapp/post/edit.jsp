<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<div class="container">
	<div class="content-wrap">
		<h1 class="page-title">書こう！</h1>

		<div class="write-form-container">
			<form name="my" method="post" action="/pst/editaction.do"
				enctype="multipart/form-data" onsubmit="return check()">
				<input type="hidden" name="p_no" value="${dto.p_no}"> <input
					type="hidden" name="old_imgfile" value="${dto.imgfile}">

				<div class="form-group">
					<label for="category">カテゴリー</label> <select id="category"
						name="category" class="form-control">
						<option value="">選択してください</option>
						<option value="ANIME" ${dto.category == 'ANIME' ? 'selected' : ''}>アニメ</option>
						<option value="MOVIE" ${dto.category == 'MOVIE' ? 'selected' : ''}>映画</option>
						<option value="GAME" ${dto.category == 'GAME' ? 'selected' : ''}>ゲーム</option>
					</select>
				</div>

				<div class="form-group">
					<label for="title">タイトル</label> <input type="text" id="title"
						name="title" class="form-control" value="${dto.title }">
				</div>

				<div class="form-group">
					<label for="content">内容</label>
					<textarea id="content" name="content" class="form-control">${dto.content}</textarea>
				</div>

				<div class="form-group">
					<label for="file">イメージを追加</label>
					<c:if test="${not empty dto.imgfile}">
						<p>${dto.imgfile}</p>
					</c:if>
					<input type="file" id="imgfile" name="imgfile" class="form-control">
				</div>

				<div class="btn-wrap">
					<a href="list.jsp" class="btn btn-cancel">キャンセル</a>
					<button type="submit" class="btn btn-submit">投稿する</button>
				</div>

			</form>
		</div>
	</div>
</div>

<script>
	function check() {

		// 카테고리
		if (!my.category.value) {
			alert("カテゴリーを選択してください。");
			my.category.focus();
			return false;
		}

		// 제목
		if (!my.title.value.trim()) {
			alert("タイトルを入力してください。");
			my.title.focus();
			return false;
		}

		// 내용
		if (!my.content.value.trim()) {
			alert("内容を入力してください。");
			my.content.focus();
			return false;
		}

		// 내용 최소 길이 10글자 이상
		if (my.content.value.trim().length < 10) {
			alert("内容は10文字以上入力してください。");
			my.content.focus();
			return false;
		}

		// 파일 확장자 체크
		if (my.imgfile.value) {
			const fileName = my.imgfile.value.toLowerCase();
			const allowed = /\.(jpg|jpeg|png|gif|webp)$/;

			if (!allowed.test(fileName)) {
				alert("画像ファイル（jpg, png, gif, webp）のみアップロード可能です。");
				my.imgfile.value = "";
				return false;
			}
		}

		alert("投稿が完了しました！");
		return true;

	}
</script>
<!-- .container 끝 -->
<%@include file="../footer.jsp"%>