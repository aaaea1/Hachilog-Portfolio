<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>

<title>Hachilog - login</title>

<div class="container">
	<div class="content-wrap">

		<h1 class="page-title">ログイン</h1>

		<div class="write-form-container">

			<form id="loginform">
				<!-- 아이디 -->
				<div class="form-group">
					<label for="userid">ID</label> <input type="text" id="loginuserid"
						name="userid" class="form-control" placeholder="IDを入力してください。"
						required> <input type="checkbox" name="useridcheck"
						id="saveid">&nbsp;IDを保存
				</div>

				<!-- 비밀번호 -->
				<div class="form-group">
					<label for="password">パスワード</label> <input type="password"
						id="password" name="password" class="form-control"
						placeholder="パスワードを入力してください。" required>
				</div>

				<!-- 버튼 -->
				<div class="btn-wrap">
					<button type="button" class="btn btn-login" id="btn-login">ログイン</button>
				</div>
				<p id="errmsg"></p>
			</form>
		</div>

	</div>
</div>

<script>

if ($.cookie("saveid")) { // saveid 라는 쿠키가 있으면 아래를 실행
	$("#loginuserid").val($.cookie("saveid")); // saveid : 쿠키명
	// 아이디 입력에 쿠키 값을 넣는다.
	$("#saveid").prop("checked", true);
	// 체크박스를 checked 상태로 함
}

$("#btn-login").click(function() {
	let userid = $("#loginuserid").val();
	let password = $("#password").val();

	if (!userid) {
		$("#errmsg").html("<span style='color:#f00'>IDを入力してください。</span>");
		return;
	}
	if (!password) {
		$("#errmsg").html("<span style='color:#f00'>パスワードを入力してください。</span>");
		return;
	}

	$.ajax({
		type: "POST",
		url: "/mem/loginaction.do",
		data: { userid: userid, password: password },
		success: function(result) {
			if (result == "success") { // 로그인이 성공하면

				// userid 쿠키 저장
				if ($("#saveid").is(":checked")) { // saveid 태그가 checked 면 
					$.cookie("saveid", userid, { expires: 7, path: "/" });
					// 쿠키 이름, 쿠키 값, 옵션 (7일간유지,웹사이트 전체경로)
				} else {
					$.removeCookie("saveid", { path: "/" });
					// 쿠키 이름, 옵션(웹사이트 전체경로)
				}

				location.href = "/main.do"; 
			} else { // 로그인 실패
				$("#errmsg").html("<span style='color:#f00'>IDまたはパスワードを確認してください。</span>");
			}
		}, error: function() {
			$("#errmsg").html("<span style='color:#f00'>error</span>");
		}
	})

})

</script>

<%@include file="../footer.jsp"%>
