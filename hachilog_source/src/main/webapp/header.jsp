<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hachilog</title>
<link rel="stylesheet" href="/css/style.css">
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/jquery.cookie.min.js"></script>
</head>
<body>

<c:if test="${sessionScope.role == 'ADMIN'}">
    <div class="admin-top-bar">
        <span>●</span>管理者モードでログインしています。
    </div>
</c:if>

	<header class="site-header">
		<a href="/main.do"> <img
			src="${pageContext.request.contextPath}/img/logo.png" class="logo">
		</a>

		<c:choose>
			<c:when test="${empty sessionScope.userid}">
				<div class="menu-row">
					<div class="menu-item" data-content="list">
						<a href="${pageContext.request.contextPath}/pst/list.do"> <span
							class="heart">♥</span> <span class="text">もっと見る</span>
						</a>
					</div>

					<div class="menu-item" data-content="login">
						<a href="${pageContext.request.contextPath}/mem/login.do"> <span
							class="heart">♥</span> <span class="text">ログイン</span>
						</a>
					</div>

					<div class="menu-item" data-content="signup">
						<a href="${pageContext.request.contextPath}/mem/join.do"> <span
							class="heart">♥</span> <span class="text">はじめる</span>
						</a>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="menu-row">
					<div class="menu-item" data-content="list">
						<a href="${pageContext.request.contextPath}/pst/list.do"> <span
							class="heart">♥</span> <span class="text">もっと見る</span>
						</a>
					</div>
					<div class="menu-item" data-content="list">
						<a href="${pageContext.request.contextPath}/pst/write.do"> <span
							class="heart">♥</span> <span class="text">書く</span>
						</a>
					</div>

					<c:choose>
						<c:when test="${sessionScope.role == 'ADMIN'}">
							<div class="menu-item" data-content="admin">
								<a href="${pageContext.request.contextPath}/adm/admin.do">
									<span class="heart">♥</span> <span class="text">会員管理</span>
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="menu-item" data-content="mypage">
								<a href="${pageContext.request.contextPath}/mem/mypage.do">
									<span class="heart">♥</span> <span class="text">マイページ</span>
								</a>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="menu-item" data-content="logout">
						<a href="${pageContext.request.contextPath}/mem/logout.do"> <span
							class="heart">♥</span> <span class="text">ログアウト</span>
						</a>
					</div>
				</div>
			</c:otherwise>

		</c:choose>
	</header>