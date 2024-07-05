<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시/도별 대기 오염정보</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<h2>어느 지역이 공기가 궁금하세요</h2>
	<select id="sidoName">
		<option>서울</option>
		<option>부산</option>
		<option>대구</option>
		<option>인천</option>
		<option>광주</option>
		<option>대전</option>
		<option>울산</option>
		<option>경기</option>
		<option>강원</option>
		<option>충북</option>
		<option>충남</option>
		<option>전북</option>
		<option>전남</option>
		<option>경북</option>
		<option>경남</option>
		<option>제주</option>
		<option>세종</option>
	</select>
	<br>
	<button class="btn btn-info" id="btn-json">클릭</button>
	<button class="btn btn-info" id="btn-xml">xml버튼</button>
	<br>
	<table class="table table-hover">
		<thead class="d-inline p-2 text-bg-dark">
			<tr>
				<td>측정소명</td>
				<td>측정일시</td>
				<td>미세먼지농도</td>
				<td>오존농도</td>
				<td>통합 대기환경 수치</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th></th>
			</tr>
		</tbody>
	</table>
	<script>
        $(() => {
            $('#btn-json').on('click', () => {
                const selectValue = $('#sidoName').val();
                
                $.ajax({
                    url: 'pollution',
                    data: {
                        sidoName: selectValue
                    },
                    type: 'get',
                    success: result => {
                        
                        //console.log(result);
                        const response = JSON.parse(result);
                        const items = response.response.body.items;
                        
                        let strEl = '';
                        
                        for (let i in items) {
                            const item = items[i];
                            strEl += '<tr>'
                                  + '<td>' + item.stationName + '</td>'
                                  + '<td>' + item.dataTime + '</td>'
                                  + '<td>' + item.o3Value + '</td>'
                                  + '<td>' + item.pm10Value + '</td>'
                                  + '<td>' + item.khaiValue + '</td>'
                                  + '</tr>';
                        };
                        
                        $('tbody').html(strEl);
                    }
                });
            });
            
            $('#btn-xml').on('click', () => {
               
                $.ajax({
                    url: 'pollution/xml',
                    data: {
                        sidoName: $('#sidoName').val()
                    },
                    type: 'get',
                    success: result => {
                        //console.log(result);
                      const items = $(resutl).find('items');
                      
                      let value ='';
                      items.each((im item) => {
                    	//console.log(item);
                    	//conseole.log($)
                    	value += '<tr>'
                    		  + '<td>' + $(item).find('stationName').text() + '</td>'
                              + '<td>' + $(item).find('dataTime').text() + '</td>'
                              + '<td>' + $(item).find('o3Value').text() + '</td>'
                              + '<td>' + $(item).find('pm10Value').text() + '</td>'
                              + '<td>' +  $(item).find('khaiValue').text() + '</td>'
                              + '</tr>';
                      });
                        
                      $('tbody').html(value);
                        
                    }
                });
            });
        });
    </script>
</body>
</html>
