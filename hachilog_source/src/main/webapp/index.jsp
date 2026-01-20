<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>

<div class="mainvisual">
	<div class="visual-inner">
		<div class="visual-title">
			<h2>好きなこと、好きな言葉で</h2>
		</div>
	</div>
</div>

<div class="container">

	<div class="content-wrap">

		<h1 class="page-title">最新記事</h1>

		<div class="card-wrap">
			<c:forEach var="item" items="${list}">

				<div class="card">
					<a
						href="${pageContext.request.contextPath}/pst/view.do?p_no=${item.p_no}"
						class="card-link"> <img src="/postimg/${item.imgfile}"
						alt="thumb">
						<div class="card-body">
							<h3>${item.title}</h3>
							<p>${item.regdate}</p>
						</div>
					</a>
				</div>

			</c:forEach>
		</div>
		<!-- .card-wrap 끝 -->


	</div>
	<!-- .content-wrap 끝 -->

</div>
<!-- .container 끝 -->

<%@include file="../footer.jsp"%>