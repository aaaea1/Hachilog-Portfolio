<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../header.jsp"%>

<div class="container adminpage-container">
	<h1 class="page-title">会員管理</h1>

	<div class="adminpage-grid">
		<section class="content-box adminpage-users">

			<c:choose>
				<c:when test="${not empty members}">
					<table class="admin-table"
						style="width: 100%; border-collapse: collapse;">
						<thead>
							<tr
								style="background-color: #f8f9fa; border-bottom: 2px solid #dee2e6;">
								<th style="padding: 12px;">ユーザーID</th>
								<th>ニックネーム</th>
								<th>メールアドレス</th>
								<th>登録日</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="m" items="${members}">
								<tr style="border-bottom: 1px solid #eee; text-align: center;">
									<td style="padding: 12px;"><c:out value="${m.userid}" /></td>
									<td><c:out value="${m.nickname}" /></td>
									<td><c:out value="${m.email}" /></td>
									<td><c:out value="${m.regdate}" /></td>
									<td>
										<form
											action="${pageContext.request.contextPath}/adm/forceDeleteMember.do"
											method="post"
											onsubmit="return confirm('${m.userid}様を強制退会させますか？');">
											<input type="hidden" name="userid" value="${m.userid}" />
											<button type="submit" class="btn btn-danger"
												style="background-color: #ff4d4d; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">
												強制退会</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="content-box adminpage-empty"
						style="text-align: center; padding: 50px;">
						<p>登録された会員がいません。</p>
					</div>
				</c:otherwise>
			</c:choose>
		</section>
	</div>
</div>

<%@ include file="../footer.jsp"%>