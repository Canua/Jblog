<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<style type="text/css">
table {
	width : 700px;
	margin: auto;
}
.post-list {
	border : 1px solid #777, border-collapse:collapse;
}
.post-list th, .post-list td { 
	border: 1px solid #777; padding:3px;
}

</style>

</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="main"/>
		</c:import>
		<form class="search-form" action="${pageContext.request.contextPath }/user/search" method="get" >
			<fieldset>
				<input type="text" name="keyword" id="keyword" />
				<input type="submit" value="검색" />
			</fieldset>
			<fieldset>
				<input type="radio" name="which" value="blog-title"> <label>블로그 제목</label>
				<input type="radio" name="which" value="tag"> <label>태그</label>
				<input type="radio" name="which" value="blog-user"> <label>블로거</label>
			</fieldset>
		</form>
	</div>
	<div style="border: 1px solid black; margin:auto; width:700px; height: auto">
		<table class = "post-list">
					<tr>
						<th>No</th>
						<th>이름</th>
						<th>블로그 방문하기</th>
						<th>가입일</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list}" var="vo" varStatus="status">
					<tr>
						<th>[${count-status.index }]</th>
						<th>${vo.name }</th>
						<th><a href="${pageContext.request.contextPath}/${vo.id}">[${vo.name }]님의 블로그 </a></th>
						<th>${vo.join_date }</th>
					</tr>
					</c:forEach>
		</table>
	</div>
</body>
</html>
