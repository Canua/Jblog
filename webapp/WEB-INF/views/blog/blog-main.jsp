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
}
.post-list {
	border : 1px solid #777, border-collapse:collapse;
}
.post-list th, .post-list td { 
	border: 1px solid #777; padding:6px;
}

</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/blog-header.jsp">
				<c:param name="menu" value="main"/>
			</c:import>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${mainpost.title }</h4>
					<p>
						${mainpost.content }
					<p>
				</div>
				<table class = "post-list">
					<tr>
						<th>제목</th>
						<th>작성일</th>
						<th>카테고리명</th>
					</tr>
					<c:forEach items="${postlist}" var="postvo" varStatus="status">
					<c:choose>
					<c:when test="${postvo.no == mainpost.no }">
					<tr>
						<td><a href="${pageContext.request.contextPath}/${authUser.id }/${postvo.category_no}/${postvo.no }">${postvo.title }<span style="color:red">[선택중]</span></a></td>
						<td>${postvo.reg_date }</td>
						<td>${postvo.content }</td>
					</tr>
					</c:when>
					<c:otherwise>
					<tr>
						<td><a href="${pageContext.request.contextPath}/${authUser.id }/${postvo.category_no}/${postvo.no }">${postvo.title }</a></td>
						<td>${postvo.reg_date }</td>
						<td>${postvo.content }</td>
					</tr>
					</c:otherwise>
					</c:choose>
					</c:forEach>
				</table>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img id="profile" style = "width:120px" onerror="this.src='${pageContext.request.contextPath }/assets/images/default_profile.png'" src="${pageContext.request.contextPath }/${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
			<c:forEach items="${categorylist}" var="vo" varStatus="status">
			  <c:choose>
				<c:when test="${vo.count == 0 }">
					<li style="color: #0xCDCDCD;" value="${vo.no }">${vo.name  }</li>
				</c:when>
				<c:when test="${vo.no == mainpost.category_no}">
					<li value="${vo.no }"><a href="${pageContext.request.contextPath}/${userVo.id}/${vo.no}">${vo.name  } [${vo.count }개]</a><span style="color:red; float:right;">[선택중]</span></li>
				</c:when>
				<c:otherwise>
					<li value="${vo.no }"><a href="${pageContext.request.contextPath}/${userVo.id}/${vo.no}">${vo.name  } [${vo.count }개]</a></li>
				</c:otherwise>
			  </c:choose>
			</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
		</div>
	</div>
</body>
</html>