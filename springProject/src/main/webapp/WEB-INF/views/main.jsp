<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>B강의장 프로젝트</title>
<style>
.innerOuter{ height : 700px;}
</style>
</head>
<body>
	<jsp:include page="common/menubar.jsp"></jsp:include>
	<div class="innerOuter">
		<h3 align="center">게시글 조회수 TOP5</h3>
		<br>
		<a href="boardlist" style="float: right; color: lightgray; font-weight: 800; font-size: 14px">더보기</a>
		<br>
		<table class="table table-hover" align="center" id="boardList">
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일</th>
					<th>첨부파일</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		<br><br>
		<table id="board-detail" class="table">
			
		</table>
		
	</div>
	
	<script>
		$(document).on('click', '#boardList > tbody > tr', e => {
		    const boardNo = $(e.currentTarget).children().eq(0).text();
		    
		    $.ajax({
		        url: 'boards/' + boardNo,
		        type: 'get',
		        success: result => {
		            //console.log(result);
		            let value = '<tr><td colspan="3">게시글 상세보기</td></tr>';
		            
		            value += '<tr>'
		                +  '<td width="300">' + result.boardTitle + '</td>'
		                +  '<td width="600">' + result.boardContent + '</td>'
		                +  '<td width="200">' + result.boardWriter + '</td>'
		                +  '</tr>';
		                
		            document.getElementById('board-detail').innerHTML = value;
		        }
		    }); 
		});
	
		$(document).ready(() => {
		    findTopFiveBoard();
		});
		
		function findTopFiveBoard() {
		    $.ajax({
		        url: 'boards',
		        type: 'get',
		        success: boards => {
		            //console.log(board);
		            
		            let value = "";
		            
		            for(let i in boards){
		            	
		            	value += '<tr>'
		            		  +  '<td>' + boards[i].boardNo + '</td>'
		            		  +  '<td>' + boards[i].boardTitle + '</td>'
		            		  +  '<td>' + boards[i].boardWriter + '</td>'
		            		  +  '<td>' + boards[i].count + '</td>'
		            		  +  '<td>' + boards[i].createDate + '</td>'
		            		  +  '<td>';
		            		  if(boards[i].originName != null){
		            			  value +='💛';		            		  
		            		  }
		            		  + '</td></tr>';
		            }
		            $('#boardList tbody').html(value);
		        }
		    });
		}

	</script>
	<jsp:include page="common/footer.jsp"></jsp:include>
</body>
</html>