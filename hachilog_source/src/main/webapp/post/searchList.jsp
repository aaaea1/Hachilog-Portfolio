<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty list}">
	<div class="no-result">検索結果がありません。</div>
</c:if>

<c:forEach var="item" items="${list}">
	<div class="post-row">
		<img src="/postimg/${item.imgfile}" alt="thumb">
		<div class="post-info">
			<a
				href="${pageContext.request.contextPath}/pst/view.do?p_no=${item.p_no}&page=${currentPage}">
				${item.title} </a>
			<div class="post-meta">
				<span class="post-author">${item.nickname}</span> <span
					class="post-date">· ${item.regdate.substring(0,10)}</span>
			</div>
		</div>
	</div>
</c:forEach>

<div class="pagination">
	<c:if test="${startPage > 1}">
		<a href="javascript:void(0);" onclick="goPage(${startPage - 1})">&lt;</a>
	</c:if>

	<c:forEach var="i" begin="${startPage}" end="${endPage}">
		<a href="javascript:void(0);" onclick="goPage(${i})"
			class="${i == currentPage ? 'active' : ''}">${i}</a>
	</c:forEach>

	<c:if test="${endPage < totalPage}">
		<a href="javascript:void(0);" onclick="goPage(${endPage + 1})">&gt;</a>
	</c:if>
</div>
