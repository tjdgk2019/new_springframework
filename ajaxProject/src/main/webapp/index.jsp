<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Document</title>
</head>
<body>
    <h1>비동기 통신 활용법</h1>
    <h3>AJAX</h3>
    <p>Asynchronous JavaScript And XML의 약자</p>
    <!-- 
    서버로부터 데이터를 응답받아 전체 페이지를 다시 만드는 것이 아니라 이부만 내용물을 바꿀 수 있는 기법
    참고로, 우리가 그동안 a태그.form태그 이용해서 요청한 방식은 동기식 요청
    -> HTML전체를 응답받아서 브라우저는 처음부터 끝까지 화면을 렌더링해야 결과를 확이할 수 있음
    비동기 처리를 위해서는 AJAX라는 기술을 사용해야함
    구현방식- 자바스크립트 / 제이퀴리/ fetchAPI/ axios
    
   동기: 서버가 요청 처리 후 응답 html데이터가 돌아와야만 그 다음 작업이 가능
   만약 서버에서 응답페이지를 돌려주는 시간이 지연되면 무작정 기다리고 있어야 한다.
   응답데이터가 돌아오면 전체화면을 파싱
    
    
    비동기식: 현재 페이지를 그대로 유지하는 동안 중가중간에 추가 요청을 보낼 수 있음
    요청을 보낸다고 해서 다른페이지가 새롭게 렌더링 되는 것이 아님(편재 페이지가 그대로 유지)
    요청을 보내놓고 응답이 올때까지 다른 작업을 할 수 있다.
    
    비동기식 단점
    요청 후 돌아온 응답데이터를 가지고 현재 페이지에서 새로운 요소를 동적으로 만들어줘야함
    -> dom요소를 새롭게 만드는 구문을 작 익혀둬야 함
    -페이지 내 복잡도가 기하급수적으로 증가
    
    jQuery에서 AJAX통신 방법- 배열 , 객체
    
    표현법
    $.ajax({
    	속성명 : 속성값,
    	속성명 : 속성값,
    	속성명 : 속성값,
    		...
    });
    
    
    주요 속성
    - url : 요청할 url(필수로 작성)=> form태그의 action속성
    - type: 요청 전송방식(GET/POST/PUT/DELETE=> 생략 시 기본값은 GET)=> form태그의 method속성
    - data: 요청 시 전달할 값({키:벨루, 키:벨루})=? form태그의 input 요소의 value속성
    - suecces : AJAX통신 성공 시 콜백함수를 정의
    
    - error : AJAX통신 실패 시 콜백함수를 정의
    - complete: AJAX통신 성공하든 실패하든 무조건 끝나면 실행할 콜백함수
    - asunc:서버와 비동기 처리방식 설정 여부(기본값 true)
    - contentType: 요청시 데이터 인코딩 방식 정의(보내는 측의 데이터 인코딩)
    - dataType : 서버에서 응답 시 돌아오는 데이터의 형태 설정 작성 안하면 스마트하게 판단함
    - xml 트리형태
    - json 맵 형태의 구조(일반적인 데이터 구조)
    - script 자바스크립트 잋 일반 String형태의 데이터
    - html html 태그 자체를 리터하는 방식
    - text String데이터
    
     -->
    <h4>AJAX로 요청 보내고 응답 받아오기</h4>
    <h5>1. 서버로 요청 시 인자값을 전달하고 응답데이터를 받아서 화면에 출력</h5>
    <label>오늘 먹을거 : 알밥, 김치찜, 돈까스, 막국수, 샌드위치</label> <br/><br/>
    
    메뉴 : <input type="text" id="menu"> <br/>
    수량 : <input type="number" id="amount"> <br/><br/>

    
    <button>서버로 전송</button>
    
    <div id="resultMsg">
     이건 어느 속성에 들어갈까나
    <!-- 버튼 클릭 시 메뉴에 입력한 음식명과 수량에 입력한 개수를 가지고 서버에 요청해서 => 응답받은 읍압데이터를 div요소의 content요소에 출력 -->
    </div>
    <!--
    <script>
    	document.getElementById('btn').onclick = () => {
    		const menu= document.getElementById('menu').value;
    		const amount= document.getElementById('amount').value;
    		
    		$.ajax({
    			url : 'ajax1.do',	 		//필수정의 속성(매핑값)
    			type : 'get', 				//요청 시 전달방식
    			data : {                    //요청 시 전달할 값
    				menu : menuValue,
    				amount : amountValue
    			},
    			success : result => {
    				//consol.log(result);
    				const resultValue= '선택하신 메뉴'+menuValue+' '+amountValue+'개의 가격은'+resultValue+'원입니다.';
    				document.getElementById('resultMsg').innerHTML='resultValue';
    			},
    			error : () => {
    				consol.log('아마 오타');
    			}
    			
    		});
    		
    	}
    </script>
     -->
     <h3>DB에서 셀렉트문을 이용해서 조회했다는 가정하에 VO객체를 응답받아서 화면상에 출력해보기</h3>
     조회할 음식 번호 : <input type="number" id="menuNo"><br/>
     <button id="select-btn">조회</button><br>
     
     <div id='today-menu'>
     
     
     </div>
     
     
    <script>
    	window.onload = () => {
    		document.getElementById('select-btn').onclick= () =>{
    			
    			$.ajax({
    				//url:'ajax2.do',
    				url:'ajax3.do',
    				type:'get',
    				data : {
    					menuNumber : document.getElementById('menuNo').value
    				},
    				success : result => {
    					//consol.log(result);
    					const obj ={
    						"menuNumber": "1",
    						"menuName" : "순두부",
    						"price" : "9500",
    						"material" : "순두부"
    					
    					};
    					console.log(obj);
    					
    					const menu = '<ul>오늘의 메뉴 :'
								   + '<li>'+result.manuName + '</li>'
								   + '<li>'+result.price + '</li>'
								   + '<li>'+result.material + '</li>'
    					
						document.getElementById('today-menu').innerHTML = menu;
								   
    				},
    				error : e=> {
        				consol.log(e);
    				}
    			});
    			
    		};

    		
    	}
    
    
    
    </script>
    
    <br>
    <h3>조회 후 리스트를 응답받아서 출력</h3>
    <button onclick="findAll()">메뉴 전체 조회</button>
    <br>
    
    <table border="1" id="find-result">
    	<thead>
    		<tr>
    			<th>메뉴명</th>
    			<th>가격</th>
    			<th>재료</th>
    		</tr>
    	</thead>
    	<tbody id="tbody">
    		
    	</tbody>
    </table>
    <script>
    	function findAll(){
    		$.ajax({
				//url:'ajax2.do',
				url:'find.do',
				type:'get',
				success : result => {
					//consol.log(result[0].menuName);
					
					
					const tbodyEl = document.getElementById('tbody');
					

					result.map(o => {
						//console.log(o);
						//console.log(i);
					
		            const trEl = document.createElement('tr'); //Element node
		            const tdFirst = document.createElement('td');
		            
		            const firstText = document.createTextNode(o.menuName);
		            tdFirst.style.width = '200px';
		            tdFirst.appendChild(firstText);
		            console.log(tdFirst);
		            
		            const tdSecond= document.createElement('td');
		               
		            const secondText = document.createTextNode(o.price);
		            tdSecond.style.width = '200px';
		            tdSecond.appendChild(secondText);
		            console.log(tdSecond);
		            
		            const tdThird = document.createElement('td');
		            
		            const thirdText = document.createTextNode(o.material);
		            tdThird.style.width = '200px';
		            tdThird.appendChild(thirdText);
		            console.log(tdThird);
		            
		            trEl.appendChild(tdFirst);
		            trEl.appendChild(tdSecond);
		            trEl.appendChild(tdThird);
		            
		            console.log(trEl);
		            
		            tbodyEl.appendChild(trEl);
    			});
    	
    	
    	}
    
    
    </script>
    
    
    
    
    
    
    
    
    
</body>
</html>