<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../header.jsp"%>

<div class="container">


	<div class="content-wrap">

		<div class="simple-search">
			<form id="searchForm">
				<input type="text" name="keyword" value="${param.keyword}"
					placeholder="投稿者やタイトルで検索">
			</form>
		</div>

		<div class="category-filter">
			<a href="javascript:void(0);" onclick="filterCategory('', this)"
				class="category-btn active">すべて</a> <a href="javascript:void(0);"
				onclick="filterCategory('ANIME', this)" class="category-btn">アニメ</a>
			<a href="javascript:void(0);" onclick="filterCategory('GAME', this)"
				class="category-btn">ゲーム</a> <a href="javascript:void(0);"
				onclick="filterCategory('MOVIE', this)" class="category-btn">映画</a>
		</div>


		<section class="list-section">

			<div class="post-list" id="postList">
				<%@ include file="searchList.jsp"%>
			</div>


		</section>
		<!-- .list-section 끝 -->

	</div>
	<!-- .content-wrap 끝 -->

</div>
<!-- .container 끝 -->

<%@include file="../footer.jsp"%>

<script>

document.getElementById("searchForm").addEventListener("submit", function(e) {
	e.preventDefault(); // 페이지 이동 막기

	const keyword = this.keyword.value;

	fetch("/pst/searchList.do?keyword=" + encodeURIComponent(keyword))
		.then(res => res.text())
		.then(html => {
			document.getElementById("postList").innerHTML = html;
		});
});

function filterCategory(category, element) {
    // 1. 모든 카테고리 버튼에서 active 클래스 제거
    $(".category-btn").removeClass("active");
    // 2. 현재 클릭한 버튼(element)에만 active 클래스 추가
    $(element).addClass("active");

    $.ajax({
        url: "/pst/searchList.do",
        type: "get",
        data: { "category": category },
        dataType: "html", 
        success: function(data) {
            $("#postList").html(data);
        },
        error: function() {
            alert("失敗");
        }
    });
}

function goPage(page) {
    // 현재 'active' 클래스가 붙어있는 카테고리 버튼에서 카테고리 명을 알아낸다.
    // filterCategory(category, element) 호출 시 세팅된 값을 활용하거나 
    // 아래처럼 active 클래스를 가진 버튼의 텍스트나 속성을 활용한다
    const activeBtn = $(".category-btn.active");
    
    // 만약 filterCategory 호출 시 넘겼던 파라미터 값을 찾기 어렵다면, 
    // 아래처럼 속성에서 직접 추출하거나 전역 변수를 활용
    // 여기서는 간단하게 현재 활성화된 버튼을 기준으로 처리
    let category = "";
    const onclickAttr = activeBtn.attr("onclick");
    if(onclickAttr) {
        const match = onclickAttr.match(/'([^']+)'/);
        if(match) category = match[1];
    }
    
    const keyword = $("input[name='keyword']").val(); // 검색어도 있다면 유지

    $.ajax({
        url: "/pst/searchList.do",
        type: "get",
        data: { 
            "category": category, 
            "keyword": keyword,
            "page": page 
        },
        success: function(data) {
            // 리스트와 페이징이 들어있는 searchList.jsp 통째로 교체
            $("#postList").html(data);
            
        }
    });
}
</script>
