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
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
var count = 0;

(function ($) {
	$.fn.hello = function () {
		console.log( $(this).attr("id") + "-----> hello" );
	}
})(jQuery);

var isEnd = false;

var messageBox = function (title, message) {
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal: true,
		buttons: {
			"확인": function () {
				$(this).dialog("close");
			}
		}
	});
}

var render = function(vo, mode){
		count++;
		console.log(count);
		if(vo.count == null || vo.count == 0){
		var htmls = 
			"<tr data-no='" + vo.no + "'>" +
			"<td>" + count + "</td>" +
			"<td>" + vo.name + "</td>" +
			"<td>" + 0 + "</td>" +
			"<td>" + vo.description + "</td>" +
			"<td>" + "<img src ='${pageContext.request.contextPath}/assets/images/deletes.jpg' data-no='"+ vo.no + "'>" + "</td>" +
			"</tr>";
		} else {
		var htmls = 
			"<tr data-no='" + vo.no + "'>" +
			"<td>" + count + "</td>" +
			"<td>" + vo.name + "</td>" +
			"<td>" + vo.count + "</td>" +
			"<td>" + vo.description + "</td>" +
			"<td>" + "</td>" +
			"</tr>";
		}
		if( mode == true ) {
			$(".admin-cat").append(htmls);		
		}else {
			$(".admin-cat").append(htmls);		
		}
}
var fetchList = function(){
	if(isEnd == true){
		return;
	}
	var ids = ${authUser.id }
	$.ajax({
		url: "${pageContext.request.contextPath }/category/ajax/list?id=" + ids ,
		type: "get",
		dataType: "json",
		data:"",
		success: function(response){
			console.log(response.data);
			if(response.result == "fail"){
				console.warn(response.data);
				return;
			}
			console.log(response.data);
					
			// rendering
			$.each(response.data, function(index, vo){
				render(vo, false);
			});
		},
		error: function(xhr, status, e){
			console.error(status + ":" + e);
		}
	});
}
$(function () {
	var clickNo = 0;
	var no = ${userVo.no };
	var id = ${authUser.id }
	// live event
	$(document).on("click", ".admin-cat td img", function (event) {
		event.preventDefault();
		var click_No = $(this).data("no");
		clickNo = click_No;
		console.log("clicked!!" + ":" + $(this).data("no"));
		
		dialogDelete.dialog("open");
	});
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen : false,
		modal: true,
		buttons: {
			"삭제": function () {
				var password = $("#password-delete").val();
				console.log("ajax 삭제");
				console.log(password);
				console.log(clickNo);
				// 지워졌다는 결과를 보고 pwd가 맞나 안맞나 확인 후 ajax를 이용해서 삭제시킨다.
				$.ajax({
					url: "${pageContext.request.contextPath }/category/ajax/delete/${authUser.id }",
					type: "post",
					dataType: "json",
					data: "clickNo=" + clickNo +
						    "&password=" + password, 
							success: function (response){
							console.log("response.result : " + response.result);
							if(response.result =="fail"){
								console.warn(response.data);
								return;
							}
				 			if(response.data == "-1"){
								console.log(response.data); 
				 				$(".error").attr("style", "display:block").html();
				 				$("#dialog-delete-form input[type='password']").val('');
				 			}else{
								//remove
								console.log("삭제됨 : " + response.data); 
								$(".admin-cat tr[data-no='"+ response.data +"']").remove();
								$("#dialog-delete-form input[type='password']").val('');
								$("#dialog-delete-form").dialog("close");
				 			}		
						},
					error: function (xhr, status, e) {
						console.log(status + ":" + e);
					}
				})
				
			},
			"취소": function () {
				$("#password-delete").val = "";
				dialogDelete.dialog("close");
			}
		},
		// 삭제 후 처리해야 할 함수, 취소했을 때 남아있는 비밀번호를 공백 처리
		close: function () {
			$("#password-delete").val = "";
			console.log("close 시 뒤처리");
		}
	});
	
	$("#add-form").submit(function (event) {
		console.log(no);	
		// submit의 기본동작(post)
		// 막아야 한다.
		event.preventDefault();
		// validate form data
		var name = $("#input-name").val();
		var description = $("#description").val();
		if(name == ""){
			name = "미분류";
		}
		if(description == ""){
			messageBox("글남기기", "카테고리에 대한 설명은 필수 입력 항목입니다.");
			$("#description").focus();
			return false;
		}
		$.ajax({
			url: "${pageContext.request.contextPath }/category/ajax/insert",
			type: "post",
			dataType: "json",
			data:  "name=" + name +
					"&description=" + description +
					"&no=" + no,
					success: function (response) {
					console.log("ajax-Insert 실행");
					render(response.data, true);
					},
					error: function (xhr, status, e) {
					console.log(status + ":" + e);
				}
			});
		});
});

$(function(){
	fetchList();
});


</script>


</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/blog-header.jsp">
				<c:param name="menu" value="admin-category"/>
			</c:import>
		</div>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/blog-navigation.jsp">
					<c:param name="menu" value="category"/>
				</c:import>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>게시물 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
				</table>
      			<form id="add-form" action="" method="post">
					<h4 class="n-c">새로운 카테고리 추가</h4>
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input id="input-name" type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input id="description" type="text" name="desc"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table> 
		      	</form>
		      	<div id="dialog-message" title="" style="display:none">
  					<p style = "padding : 20px 0"></p>
				</div>
				<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
	              <p class="validateTips normal">계정의 비밀번호를 입력하세요.</p>
	              <p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
	      	    <form>
	               <input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
	               <input type="hidden" id="hidden-no" value="">
	               <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
	         	</form>
        	</div>
			</div>
		</div>
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/blog-footer.jsp"/>
		</div>
	</div>
</body>
</html>