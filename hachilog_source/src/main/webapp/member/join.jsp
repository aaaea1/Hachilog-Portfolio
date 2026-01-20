<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>

<div class="container">
	<div class="content-wrap">

		<h1 class="page-title">会員登録</h1>


		<div class="write-form-container">

			<form name="form" id="form">
				<!-- 아이디 -->
				<div class="form-group">
					<label for="userid">ユーザーID</label> <input type="text" id="userid"
						name="userid" class="form-control" placeholder="ユーザーIDを入力してください。"
						required>
					<div class="userid-msg"></div>
				</div>

				<!-- 이름 -->
				<div class="form-group">
					<label for="name">ニックネーム</label> <input type="text" id="nickname"
						name="nickname" class="form-control"
						placeholder="ニックネームを入力してください。" required>
					<div class="nickname-msg"></div>
				</div>

				<!-- 비밀번호 -->
				<div class="form-group">
					<label for="password">パスワード</label> <input type="password"
						id="password" name="password" class="form-control"
						placeholder="パスワードを入力してください。" required>
					<div class="password-msg"></div>
				</div>

				<!-- 비밀번호 확인 -->
				<div class="form-group">
					<label for="pw2">パスワード確認</label> <input type="password" id="pw2"
						name="pw2" class="form-control" required>
					<div class="pw2-msg"></div>
				</div>

				<!-- 이메일 -->
				<div class="form-group">
					<label for="email">メールアドレス</label> <input type="email" id="email"
						name="email" class="form-control" placeholder="メールアドレスを入力してください。"
						required>
					<div class="email-msg"></div>
				</div>

				<!-- 전화번호 -->

				<div class="form-group">
					<label for="phone">電話番号</label> <input type="text" id="phone"
						name="phone" class="form-control" placeholder="電話番号を入力してください。"
						required>
					<div class="phone-msg"></div>
				</div>

				<!-- 버튼 -->
				<div class="btn-wrap">
					<button type="reset" class="btn btn-cancel"
						onclick="javascript:location.href='/main.do'">キャンセル</button>
					<button type="button" id="btn-submit" class="btn btn-submit">登録する</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	$("#userid")
			.blur(
					function() {
						if (!$("#userid").val()) {
							return;
							$(".userid-msg").html(
									"<span style='color:#f00'>IDは必須です。</span>");
						}

						$
								.ajax({
									type : "POST",
									url : "/mem/idcheck.do",
									data : {
										userid : $("#userid").val()
									},
									success : function(result) {
										if (result == 1) {
											$(".userid-msg")
													.html(
															"<span style='color:#f00'>すでに使用されているIDです。</span>");
										} else {
											$(".userid-msg")
													.html(
															"<span style='color:#00f'>使用可能なIDです。</span>");
										}
									},
									error : function() {
										alert("error");
									}
								});
					});

	$("#nickname")
			.blur(
					function() {
						if (!$("#nickname").val()) {
							$(".nickname-msg")
									.html(
											"<span style='color:#f00'>ニックネームは必須です。</span>");
							return;
						}

						$
								.ajax({
									type : "POST",
									url : "/mem/nicknamecheck.do",
									data : {
										nickname : $("#nickname").val()
									},
									success : function(result) {
										if (result == 1) {
											$(".nickname-msg")
													.html(
															"<span style='color:#f00'>すでに使用されているニックネームです。</span>");
										} else {
											$(".nickname-msg")
													.html(
															"<span style='color:#00f'>使用可能なニックネームです。</span>");
										}
									},
									error : function() {
										alert("error");
									}
								});
					});

	$("#password").blur(
			function() {
				if (!$("#password").val()) {
					$(".password-msg").html(
							"<span style='color:#f00'>パスワードは必須です。</span>");
				} else {
					$(".password-msg").text("");
				}
			});

	$("#pw2").blur(
			function() {
				if (!$("#pw2").val()) {
					$(".pw2-msg").html(
							"<span style='color:#f00'>パスワード確認は必須です。</span>");
					return;
				}

				if ($("#password").val() != $("#pw2").val()) {
					$(".pw2-msg").html(
							"<span style='color:#f00'>パスワードが一致しません。</span>");
				} else {
					$(".pw2-msg").text("");
				}
			});

	$("#email").blur(
			function() {
				if (!$("#email").val()) {
					$(".email-msg").html(
							"<span style='color:#f00'>メールアドレスは必須です。</span>");
				} else {
					$(".email-msg").text("");
				}
			});

	$("#phone").blur(function() {
		if (!$("#phone").val()) {
			$(".phone-msg").html("<span style='color:#f00'>電話番号は必須です。</span>");
		} else {
			$(".phone-msg").text("");
		}
	});

	$("#btn-submit").click(
			function() {
				let isvalid = true;

				$("#userid,#nickname,#password,#pw2,#email,#phone").each(
						function() {
							if (!$(this).val()) {
								isvalid = false;
								$(this).focus();
								return false;
							}
						});

				if ($(".userid-msg").text().includes("すでに使用されているIDです。")
						|| $(".nickname-msg").text().includes(
								"すでに使用されているニックネームです。")) {
					alert("重複があります。");
					return;
				}

				if ($(".pw2-msg").text().includes("一致しません。")) {
					$("#pw2").focus();
					return;
				}

				if (isvalid) {
					$("#form").attr("method", "post");
					$("#form").attr("action", "/mem/membersave.do");
					$("#form").submit();
				}
			});
</script>

<%@include file="../footer.jsp"%>