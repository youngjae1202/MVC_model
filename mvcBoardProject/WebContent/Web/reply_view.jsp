<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>reply_view</title>
<base href="http://192.168.0.13:8181/mvcBoardProject/">
<link type="text/css" rel="stylesheet" href="Web/Style/styleSheet.css" />
<style>
table {
	width : 70%;	
	border: 1px solid black;
    border-collapse: collapse;
}
tr:nth-child(odd) {
	background-color : #e1e1e1;	
}
td {
	padding : 5px;
	border: 1px solid black;
    border-collapse: collapse;
}
input {
	padding : 7px;
	width : 95%;
}
</style>
</head>
<body>

<div class="pageTitle">댓글 처리</div>

<p>
	댓글 작성 창의 구성은 댓글로 처리 해야할 이름, 제목, 내용은 입력 가능하도록 하고 게시물의 번호와 히트수는
	보여 주기만 하며 댓글 처리에 필요한 게시물 번호,그룹번호,스텝,들여쓰기값은 숨겨두어 submit시는 서버로
	보내도록 처리함
	
	<ol>
		<li><b>METHOD : POST</b></li>
		<li><b>ACTION : reply.do</b></li>
		<li><b>버튼 : 확인(Submit), 목록 가기</b></li>		
	</ol>	
</p>

<h3>댓글 처리 창 </h3>

<table>
	<form action="reply.do" method="POST">
		<input type="hidden" name="bId" value="${reply_view.bId}">
		<input type="hidden" name="bGroup" value="${reply_view.bGroup}">
		<input type="hidden" name="bStep" value="${reply_view.bStep}">
		<input type="hidden" name="bIndent" value="${reply_view.bIndent}">
		<tr>
			<td> 게시물번호 </td>
			<td> ${reply_view.bId} </td>			
		</tr>
		<tr>
			<td> 히트 </td>
			<td> ${reply_view.bHit} </td>
		</tr>
		<tr>
			<td> 이름 </td>
			<td> <input type="text" name="bName" placeholder="이름을 입력하세요"></td>
		</tr>
		<tr>
			<td> 제목 </td>
			<td> <input type="text" name="bTitle" placeholder="${reply_view.bTitle}"></td>
		</tr>
		<tr>
			<td> 내용 </td>
			<td> <input type="text" name="bContent" placeholder="${reply_view.bContent}"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="답변" style="width:50px;">&nbsp;&nbsp; <a href="list.do" >목록</a></td>
		</tr>
	</form>	
</table>
<!-- 
	reply_view객체는 java에서 request객체에 설정한 객체로 BDto객체를 값으로 줌
	type이 hidden은 보이지는 않으나 submit시는 value값이 서버로 전송됨
	원래 게시물의 제목 및 내용은 힌트 처리로 보여 줌
 -->



</body>
</html>