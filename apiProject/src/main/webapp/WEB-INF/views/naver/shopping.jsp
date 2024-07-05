<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
.items { width:1000px; margin:auto; display: flex; flex-wrap:wrap; gap:20px;}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">KH</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
      
      <nav class="navbar bg-body-tertiary">
	  <div class="container-fluid">
	    <form class="d-flex" role="search">
	      <input id="keyword" class="form-control me-2" type="search" placeholder="상품명을 입력해주세요" aria-label="Search">
	      <button id="search-btn" class="btn btn-outline-success" type="button">Search</button>
	    </form>
	  </div>
	</nav>

    </div>
</nav>
	<hr/>
	<div id="total-count" style="display:block; padding:20px; text-align:center;">
	
	
	</div>
	
	<div class="items"></div>
	
	<button class="button btn-lg" style="background:#333; color:white; font-weight: bold; margin: auto;">다음상품</button>
	
	<script>
    var startNo = 1;

    $('#search-btn').click(() => {
        startNo = 1;
        searchItem();
    });

    function nextPage() {
        startNo += 9;
        searchItem();
    };

    function searchItem() {
        const $keyword = $('#keyword').val();
        
        $.ajax({
            url: 'product',
            type: 'get',
            data: {
                keyword: $keyword,
                start: startNo
            },
            success: product => {
                console.log(product);
                $('#total-count').fadeOut(300);
                $('#total-count').html('총 <b>' + product.total + '</b> 건의 <label style="color:red; font-weight:bold;">' + $keyword + '</label> 검색되었습니다.').fadeIn(1000);
                
                const items = product.items;
                let item = '';
                
                for (let i in items) {
                    item += '<div class="card" style="width: 18rem;">'
                         + '<img src="' + items[i].image + '" class="card-img-top" alt="...">' // 'imag e'에서 공백 제거
                         + '<div class="card-body">'
                         + '<h5 class="card-title">' + items[i].brand + '</h5>'
                         + '<p class="card-text">' + items[i].title + '</p>'
                         + '</div>'
                         + '<ul class="list-group list-group-flush">'
                         + '<li class="list-group-item">가격</li>'
                         + '<li class="list-group-item">' + items[i].lprice + '원</li>'
                         + '</ul>'
                         + '<div class="card-body">'
                         + '<a href="' + items[i].link + '" class="btn btn-primary">구매하러 가기</a>'
                         + '</div>'
                         + '</div>';
                }
                
                $('.items').html(item);
            }
        });
    }
</script>


	
	
	
</body>
</html>