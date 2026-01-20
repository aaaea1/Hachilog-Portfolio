<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>

<div class="container">
	<div class="content-wrap">

		<h1 class="page-title">会員情報修正</h1>

		<div class="write-form-container">

			<form id="form">

				<!-- 닉네임 -->
				<div class="form-group">
					<input type="hidden" id="originNickname" value="${member.nickname}">
					<label>ニックネーム</label> <input type="text" name="nickname"
						id="nickname" class="form-control" value="${member.nickname}"
						required>
					<div class="nickname-msg"></div>
				</div>

				<!-- 기존 비밀번호 -->
				<div class="form-group">
					<label>現在のパスワード</label> <input type="password" name="password"
						id="password" class="form-control" required>
				</div>

				<!-- 기존 비밀번호 확인 -->
				<div class="form-group">
					<label>現在のパスワード確認</label> <input type="password" name="pw2"
						id="pw2" class="form-control" required>
					<div class="pw-msg"></div>
				</div>

				<!-- 새 비밀번호 -->
				<div class="form-group">
					<label>新しいパスワード（変更する場合のみ）</label> <input type="password"
						name="newPassword" id="newPassword" class="form-control">
				</div>

				<!-- 새 비밀번호 확인 -->
				<div class="form-group">
					<label>新しいパスワード確認</label> <input type="password" name="newPw2"
						id="newPw2" class="form-control">
					<div class="newpw-msg"></div>
				</div>

				<!-- 이메일 -->
				<div class="form-group">
					<label>メールアドレス</label> <input type="email" name="email" id="email"
						class="form-control" value="${member.email}" required>
				</div>

				<!-- 전화번호 -->
				<div class="form-group">
					<label>電話番号</label> <input type="text" name="phone" id="phone"
						class="form-control" value="${member.phone}" required>
				</div>

				<!-- 닉네임 체크 결과 -->
				<input type="hidden" id="nicknameCheck" value="true">

				<!-- 버튼 -->
				<div class="btn-wrap">
					<button type="submit" class="btn btn-submit">修正する</button>
					<button type="button" class="btn btn-cancel"
						onclick="location.href='/mem/mypage.do'">キャンセル</button>
				</div>

			</form>
		</div>
	</div>
</div>

<script>
	/* 닉네임 중복 검사 */
	$("#nickname")
			.blur(
					function() {

						let inputNickname = $(this).val();
						let originNickname = $("#originNickname").val();

						// 닉네임 변경 안 한 경우 → 검사 통과
						if (inputNickname === originNickname) {
							$(".nickname-msg")
									.html(
											"<span style='color:blue'>現在のニックネームです。</span>");
							$("#nicknameCheck").val("true");
							return;
						}

						// 닉네임 비어 있으면 중단
						if (!inputNickname)
							return;

						// 변경한 경우만 중복 검사
						$
								.ajax({
									type : "POST",
									url : "/mem/nicknamecheck.do",
									data : {
										nickname : inputNickname
									},
									success : function(result) {
										if (result == 1) {
											$(".nickname-msg")
													.html(
															"<span style='color:red'>すでに使用されているニックネームです。</span>");
											$("#nicknameCheck").val("false");
										} else {
											$(".nickname-msg")
													.html(
															"<span style='color:blue'>使用可能なニックネームです。</span>");
											$("#nicknameCheck").val("true");
										}
									}
								});
					});

	/*  기존 비밀번호 확인 */
	$("#pw2").blur(
			function() {
				if ($("#password").val() !== $("#pw2").val()) {
					$(".pw-msg").html(
							"<span style='color:red'>現在のパスワードが一致しません。</span>");
				} else {
					$(".pw-msg").text("");
				}
			});

	/* 새 비밀번호 확인 */
	$("#newPw2").blur(
			function() {
				if ($("#newPassword").val()
						&& $("#newPassword").val() !== $("#newPw2").val()) {
					$(".newpw-msg").html(
							"<span style='color:red'>新しいパスワードが一致しません。</span>");
				} else {
					$(".newpw-msg").text("");
				}
			});

	/* 최종 검사 */
	$("#form").on(
			"submit",
			function(e) {

				// 닉네임 중복 체크
				if ($("#nicknameCheck").val() !== "true") {
					alert("ニックネームの重複チェックをしてください。");
					e.preventDefault();
					return false;
				}

				// 기존 비밀번호 필수
				if (!$("#password").val() || !$("#pw2").val()) {
					alert("現在のパスワードを入力してください。");
					e.preventDefault();
					return false;
				}

				// 기존 비밀번호 일치
				if ($("#password").val() !== $("#pw2").val()) {
					alert("現在のパスワードが一致しません。");
					e.preventDefault();
					return false;
				}

				// 새 비밀번호 입력 시 확인
				if ($("#newPassword").val()
						&& $("#newPassword").val() !== $("#newPw2").val()) {
					alert("新しいパスワードが一致しません。");
					e.preventDefault();
					return false;
				}

				this.action = "/mem/infoeditaction.do";
				this.method = "post";
			});
</script>

<%@ include file="../footer.jsp"%>
