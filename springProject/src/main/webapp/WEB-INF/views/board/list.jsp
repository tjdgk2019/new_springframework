<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Document</title>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet"
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
.content {
    background-color: rgb(247, 245, 245);
    width: 80%;
    margin: auto;
}

.innerOuter {
    border: 1px solid lightgray;
    width: 80%;
    margin: auto;
    padding: 5% 10%;
    background-color: white;
}

#boardList {
    text-align: center;
}

#boardList>tbody>tr:hover {
    cursor: pointer;
}

#pagingArea {
    width: fit-content;
    margin: auto;
}

#searchForm {
    width: 80%;
    margin: auto;
}

#searchForm>* {
    float: left;
    margin: 5px;
}

.select {
    width: 20%;
}

.text {
    width: 53%;
}

.searchBtn {
    width: 20%;
}
</style>
</head>
<body>

    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br>
        <br>
        <div class="innerOuter" style="padding: 5% 10%;">
            <h2>게시판</h2>
            <br>
            <!-- 로그인 후 상태일 경우만 보여지는 글쓰기 버튼 -->
            <c:if test="${not empty sessionScope.loginUser }">
            <a class="btn btn-secondary" style="float: right;" href="insertForm.do">글쓰기</a> <br>
            </c:if>
            <br>

            
            
            <table id="boardList" class="table table-hover" align="center">
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
	

                    <c:choose>
                        <c:when test="${board.size == 0}">
                            <tr>
                                <td colspan="6">조회된 결과가 존재하지 않습니다.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${list}" var="board">
                                <tr class="board-detail" id="boardNo-${board.boardNo}">
                                    <td>${board.boardNo}</td>
                                    <td>${board.boardTitle}</td>
                                    <td>${board.boardWriter}</td>
                                    <td>${board.count}</td>
                                    <td>${board.createDate}</td>
                                    <td><c:if test="${ not empty board.originName}">
                                           😠
                                       </c:if></td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <br>
		            <script>
               $(() => {
                  //script? : eventTarget과 eventType을 생각하기
                  $('.board-detail').click(e => {
                     //console.log('board-detail 클릭!!');
                     
                     //e.target : 이벤트가 발생한 시점에 선택한 요소
                     //e.currentTarget : 이벤트 지정하여 이벤트를 동작시킨 요소
                     const id = (e.currentTarget.id.split('-')[1]);
                     
                     //javaScript 속성값 변경
                     //객체.속성 = 속성값;
                     //URL 변경
                     location.href = "board-detail?boardNo=" + id;
                     
                     
                     // jQuery 자식 요소 찾기
                     // 선택요소.find('선택자')
                     // 선택요소.children();
                     //console.log(e.currentTarget.children()); : e.currentTarget은 순수 자바스크립트 방식으로 가져온 값이므로 jQuery의 메서드인 children()을 사용할 수 없음
                     // => console.log($(e.currentTarget).children())
                     // 내부에서 첫번째요소를 찾는 방법 : children(':first') 또는 children().first()
                     // 내부에서 n번째 요소를 찾는 방법 : children().eq(n-1)
                     //console.log($(e.currentTarget).children().eq(0).text())
                     
                     // tr click 시 상세보기 인라인 이벤트 핸들러
                     // 해당 tr요소의 onclick 속성으로 onclick="location.href=board-detail?boardNo=${board.boardNo}" 지정하는 방법
                  });
               });
            </script>
          <!-- 이동할수 있는 만큼만 페이지 -->
          <div id="pagingArea">
    <ul class="pagination">
        <!-- 이전 버튼 처리 -->
        <c:choose>
            <c:when test="${pageInfo.currentPage == 1}">
                <li class="page-item">
                    <a class="page-link" href="#" onclick="return false;">이전</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <c:choose>
                        <c:when test="${empty condition}">
                            <a class="page-link" href="boardlist?page=${pageInfo.currentPage - 1}">이전</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-link" href="search.do?page=${pageInfo.currentPage - 1}&condition=${condition}&keyword=${keyword}">이전</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:otherwise>
        </c:choose>

        <!-- 페이지 번호 -->
        <c:forEach begin="${pageInfo.startPage}" end="${ pageInfo.endPage }" var="p">
            <c:choose>
                <c:when test="${ empty condition }">
                    <li class="page-item">
                        <a class="page-link" href="boardlist?page=${p}">${p}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="search.do?page=${p}&condition=${condition}&keyword=${keyword}">${p}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!-- 다음 버튼 처리 -->
        <c:choose>
            <c:when test="${pageInfo.currentPage == pageInfo.maxPage}">
                <li class="page-item">
                    <a class="page-link" href="#" onclick="return false;">다음</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <c:choose>
                        <c:when test="${empty condition}">
                            <a class="page-link" href="boardlist?page=${pageInfo.currentPage + 1}">다음</a>
                        </c:when>
                        <c:otherwise>
                            <a class="page-link" href="search.do?page=${pageInfo.currentPage + 1}&condition=${condition}&keyword=${keyword}">다음</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>    
        <br clear="both"><br>

            <form id="searchForm" action="search.do" method="get" align="center">
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" value="${ keyword }">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            <br><br>
            
            <script>
            
            $(() => {
               $('#searchForm option[value="${condition}"]').attr('selected', true);
            })
            </script>  
          
	</div>
    
    </div>

    <jsp:include page="../common/footer.jsp" />

</body>
</html>
