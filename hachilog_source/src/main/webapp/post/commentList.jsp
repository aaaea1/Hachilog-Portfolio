<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:forEach var="item" items="${list }">
	<div class="comment-item" data-id="${item.c_no}">
		<div class="comment-meta">
			<span class="comment-author">${item.nickname}</span> <span
				class="comment-date">${item.regdate} <c:if
					test="${sessionScope.userid == item.userid}">
					<button class="btn-edit">編集</button>
				</c:if> <c:if
					test="${sessionScope.userid == item.userid || sessionScope.role == 'ADMIN'}">
					<button class="btn-delete"
						style="${sessionScope.role == 'ADMIN' && sessionScope.userid != item.userid ? 'color: red;' : ''}">
						削除</button>
				</c:if>

			</span>
		</div>

		<div class="comment-content">${item.content }</div>

	</div>
</c:forEach>

