<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>write_view</title>
<base href="http://192.168.0.13:8181/mvcBoardProject/">
<link type="text/css" rel="stylesheet" href="Web/Style/styleSheet.css" />
<style>
th,td {
	border:1px solid black;	
	padding : 5px;
}
table {
	width : 500px;
}
input {
	padding : 10px;
}
</style>
<script src="//code.jquery.com/jquery-latest.min.js">
</script>
<script>
$(document).ready(function(){
	$("#frm1").submit(function(event){ //form엘리먼트의 submit이벤트 처리 메서드
		
		$.ajax({ //.ajax메서드는 Ajax로 서버에 요청시 사용하는 메서드
			type : $("#frm1").attr("method"),  
			url : $("#frm1").attr("action"),
			data : $("#frm1").serialize(),			
			success : function(data) {
				alert("Ajax 데이터 처리 성공");
				location.href = "list.do";				
			}			
		});
	    //원래의 submitt기능 비활성화
		event.preventDefault();		
	});	
});
</script>
</head>
<body>

<div class="pageTitle">게시판 등록하기</div>

<p>
	게시판 등록은 작성자 이름, 제목, 게시판 내용을 작성하여 form 엘리먼트를 이용하여 서버로 보냄
	<ol>
		<li><b>FrontController서브렛으로 보냄 :&nbsp;</b></li>
		<li><b>서버로 보낼시는 POST방식으로 보냄 :&nbsp;</b></li>
		<li><b>DB에 작성 내용 저장후 다시 목록창으로 화면을 변경해줌</b></li>	
		<li><b>Submit는 AJAX의 ajax()메서드로 보내줌</b></li>	
	</ol>	
</p>

<h3>게시판 입력창</h3>

<table cellpadding="0" cellspacing="0">
	<form id="frm1" action="write.do" method="post">
		<tr>
			<td>이름</td>
			<td><input type="text" name="bName" size = "80" placeholder="게시자 이름"></td>
		</tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="bTitle" size = "80" placeholder="게시물 제목"></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea name="bContent" rows="10" cols="82" placeholder="게시판 내용" ></textarea></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="AJAX Submit">&nbsp;&nbsp;
			<a href="list.do" target="content">목록보기</a>
			</td>			
		</tr>
	</form>
</table>

</body>
</html>