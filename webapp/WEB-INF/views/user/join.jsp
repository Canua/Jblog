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
<script type ="text/javascript" src = "${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#join-form").submit(function(){	
		if($("#name").val() == ""){
			alert("이름은 필수 입력 항목입니다.");
			$("#name").focus();
			return false;
		}
		
		if($("#blog-id").val() == ""){
			alert("id는 필수 입력 항목입니다.");
			$("#blog-id").focus();
			return false;
		}
		
		if($("#img-checkid").is(":visible") == false){
			alert("id 중복 체크를 해야합니다.");
			return false;
		}
		
		if($("input[type='password']").val() == ""){
			alert("비밀번호는 필수 입력 항목입니다.");
			$("input[type='password']").focus();
			return false;
		}
		
		if($("#agree-prov").is(":checked") == false){
			alert("필수 약관 동의를 해야합니다.");
			return false;
		}
		return true;
	});
	
	$("#blog-id").change(function () {
		$("#btn-checkid").show();
		$("#img-checkid").hide();
	});
	
	$("#btn-checkid").click(function () {
		var id = $("#blog-id").val();
		if(id == ""){
			return
		}
		
		$.ajax( {
			url : "${pageContext.request.contextPath }/user/api/checkid?id=" + id,
			type: "get",
			dataType: "json",
			data: "",
			success: function (response) {
				console.log(response);
				// 사용 불가능한 이메일
				if(response.data == true){ 
					alert("이미 존재하는 id입니다. 다른 id를 사용해 주세요.");
					$("#blog-id").val("").focus();
					
					return;
				}
				// 사용가능한 이메일
				$("#btn-checkid").hide();
				$("#img-checkid").show();
				
			},
			error: function (xhr, status, e) {
				console.log(status + ":" + e);
			}
		});
	});
});
</script>
</head>
<body>
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="join"/>
		</c:import>
		<form class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name">이름</label>
			<input id="name" name="name" type="text" value="">
			
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text"> 
			<input id="btn-checkid" type="button" value="id 중복체크">
			<img id="img-checkid" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<input type="password"  id="password" name="password" />
			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>
</body>
</html>
