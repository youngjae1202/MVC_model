<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>content_view</title>
<base href="http://192.168.0.13:8181/mvcBoardProject/">
<link type="text/css" rel="stylesheet" href="Web/Style/styleSheet.css" />
<style>
input {
	padding : 8px;
	margin : 5px;
}
form {
	width : 800px;
}
</style>
</head>
<body>

<div class="pageTitle">게시판 내용 보기</div>

<p>
	게시판 내용 보기는 게시판 제목 클릭시 DB로 부터 해당 게시물의 주키인 번호를  이용하여 조회하여 해당
	레코드를 가져와 화면에 출력 해주며 이 창에 다른 메뉴(수정,삭제,댓글 적성,목록 보기)로 이동하는 버튼을
	만들어 줌
	<ol>
		<li><b>번호(DB의 primary key) : </b></li>
		<li><b>이름 : </b></li>
		<li><b>히트 수 : </b></li>
		<li><b>제목 : </b></li>
		<li><b>내용 : </b></li>
		<li><b>메뉴이동(수정,삭제,댓글,목록) : </b></li>
	</ol>	
</p>

<h3>게시판 내용 보기 창 </h3>

<form action="modify.do" method="post">
	<fieldset>
		<legend>게시판 내용 보기 및 수정:</legend>
		<input type="hidden" name="bId" value="${content_view.bId}"><br/>
		번호 : <input type="text" value="${content_view.bId}" readonly> <br/>
		히트 : <input type="text" value="${content_view.bHit}" readonly><br/>
		이름 : <input type="text" name="bName" value="${content_view.bName}"><br/>
		제목 : <input type="text" name="bTitle" value="${content_view.bTitle}"><br/>
		내용 : <br/></br>
		<textarea rows="10" name="bContent" cols="80">${content_view.bContent}</textarea>
		<br/><br/>
		<input type="submit" value="수정"> &nbsp;&nbsp;<a href="list.do" target="content">
		목록보기 </a> &nbsp;&nbsp; <a href="delete.do?bId=${content_view.bId}" 
		target="content">삭제</a> &nbsp;&nbsp;
		<a href="reply_view.do?bId=${content_view.bId}" target="content">댓글</a>		
	</fieldset>
</form>

<!-- 
fieldset엘리먼트는 form엘리먼트의 테두리선과 제목을 legend를 이용하여 만들어 주는 엘리먼트
input의 type이 text인 것은 수정이 가능(readonly는 수정을 못하게 함)
bId를 hidden으로 하여 DB의 primary key인 bId컬럼을 수정 못하게 하기 위함
 -->

</body>
</html>



