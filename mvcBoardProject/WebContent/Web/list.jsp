<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<base href="http://192.168.0.13:8181/mvcBoardProject/">
<link type="text/css" rel="stylesheet" href="Web/Style/styleSheet.css" />
<title>List</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>
<script>
$(document).ready(function(){
	$("#myInput").on("keyup",function(){ //키보드를 눌럿다 띨대 발생하는 이벤트 keyup처리
		var value = $(this).val().toLowerCase(); //myInput엘리먼트의 입력값(val()을 소문자로 변환해서 변수 
		//value에 저장
		$("#myTable tr").filter(function(){
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
			//toggle()메서드는 일치하지 않은 행은 display:none으로 처리
		});
	});
});
</script>
<style> 
table {
	width : 70%;	
	border: 1px solid black;
    border-collapse: collapse;
}
tbody tr:nth-child(odd) {
	background-color : #e1e1e1;	
}
td,th {
	padding : 5px;
	border: 1px solid black;
    border-collapse: collapse;
}
input {
	width : 180px;
	padding : 5px;
}	
</style>
</head>
<body>

<div class="pageTitle">MVC로 개발한 게시판 프로젝트</div>

<p>
	게시판 프로젝트에서는 사용한 기술은 다음과 같음<br/>
	<b>Web화면은 HTML5, CSS, JS, Jquery를 활용</b>
	<b>Web 서버는 JSP&Servlet를 이용하여 MVC모델로 개발하고 DBMS는 Oracle11g를 이용</b>
	<!-- ol엘리먼트로 메서드의 파라메터 항목 리스트를 만듬 -->
	<ol>
		<li><b>웹화면 구성:</b> HTML,HTML5,CSS,CSS3,자바스크립트,JQuery</li>
		<li><b>웹서버:</b> 톰캣서버를 이용한 JSP,SERVLET 개발</li>
		<li><b>디자인 패턴:</b> MVC모델이용하고 FrontController와 Command콤포넌트로 구성</li>
		<li><b>DB:</b> 오라클과 접속은 DBCP API활용</li>
		<li><b>검색:</b> 검색은 JQuery를 이용하여 검색</li>
	</ol>	
</p>

<h3>MY MVC BOARD</h3>
<br/><br/>
검색 : <input id="myInput" type="text" placeholder="Search..">
<br/><br/>

<table>
    <!-- 1행은 항목(컬럼)을 표시하는 제목줄 -->
    <thead>
    	<tr>
			<th>번호</th>
			<th>이름</th>
			<th>제목</th>
			<th>날짜</th>
			<th>히트</th>		
		</tr>
    </thead>
	
	<!-- 2행은 게시판 정보가 표시된 줄로 jsp를 이용하여 다수개의 게시판 정보를 표시한다
	 jstl의 반복문인 forEach를 사용하여 반복 시킴  jstl의 사용은 prefix:명령 처리할 내용 /prefix:명령
	 forEach는 반복문으로 items는 반복할 객체(list)를 지정하고 var인 dto에 items를 반복하여 얻은 값을 저장
	 (배열형태인 list를 반복하여 값을 얻어냄) -->
	 <tbody id="myTable">
	 	<c:forEach items="${list}" var="dto">
		<tr>
			<td>${dto.bId}</td>
			<td>${dto.bName}</td>		
			<td>
				<!-- 댓글 처리는 복수개가 될수 있으므로 forEach -->
				<c:forEach begin="1" end="${dto.bIndent}">-</c:forEach>
				<!-- 제목클릭시 내용보기로 이동토록 링크 처리 -->
				<a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a>
				<!-- 제목클릭시 get방식으로 bId를 파라메터로해서 content_view.do로 리퀘스트함 -->
			</td>
			<td>${dto.bDate}</td>
			<td>${dto.bHit}</td>
		</tr>		
		</c:forEach>
	</tbody>
	<tfoot>	
		<!-- 글작성 메뉴와 목록 메뉴를  만들어 놓음 -->
		<tr>
			<td colspan="5"><a href="list.do" target="content">목록</a>&nbsp; &nbsp; &nbsp;
			<a href="write_view.do" target="content">글작성</a>
			</td>
		</tr>
	</tfoot>	 	
</table>

<h3>jQuery Code Sample</h3>
<pre>
$(document).ready(function(){
	$("#myInput").on("keyup",function(){ //키보드를 눌럿다 띨대 발생하는 이벤트 keyup처리
		var value = $(this).val().toLowerCase(); //myInput엘리먼트의 입력값(val()을 소문자로 변환해서 변수 
		//value에 저장
		$("#myTable tr").filter(function(){
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
			//toggle()메서드는 일치하지 않은 행은 display:none으로 처리
		});
	});
});
</pre>


</body>
</html>