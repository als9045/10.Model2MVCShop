<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<html lang="en" data-bs-theme="auto">
  <head>
  <script src="/docs/5.3/assets/js/color-modes.js"></script>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.118.2">
    <title>Album example · Bootstrap v5.3</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.3/examples/album/">

    <script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
	crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@docsearch/css@3">
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<!-- jQuery UI CDN(Content Delivery Network) 호스트 사용 -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    <!-- Favicons -->
<link rel="apple-touch-icon" href="/docs/5.3/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="/docs/5.3/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="/docs/5.3/assets/img/favicons/safari-pinned-tab.svg" color="#712cf9">
<link rel="icon" href="/docs/5.3/assets/img/favicons/favicon.ico">
<meta name="theme-color" content="#712cf9">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        width: 100%;
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }

      .btn-bd-primary {
        --bd-violet-bg: #712cf9;
        --bd-violet-rgb: 112.520718, 44.062154, 249.437846;

        --bs-btn-font-weight: 600;
        --bs-btn-color: var(--bs-white);
        --bs-btn-bg: var(--bd-violet-bg);
        --bs-btn-border-color: var(--bd-violet-bg);
        --bs-btn-hover-color: var(--bs-white);
        --bs-btn-hover-bg: #6528e0;
        --bs-btn-hover-border-color: #6528e0;
        --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
        --bs-btn-active-color: var(--bs-btn-hover-color);
        --bs-btn-active-bg: #5a23c8;
        --bs-btn-active-border-color: #5a23c8;
      }

      .bd-mode-toggle {
        z-index: 1500;
      }

      .bd-mode-toggle .dropdown-menu .active .bi {
        display: block !important;
      }
      
      <style>
        .dark-card {
            background-color: #333; /* 어두운 배경색 */
        }
        
        /* 공통 스타일 */
.button-link {
    display: inline-block;
    text-decoration: none;
    padding: 0;
    margin: 0;
    margin-left: auto; /* 이 부분을 추가하여 버튼을 오른쪽으로 이동 */
}


.bobotun, .bobotun_1 {
    /* 버튼 스타일 */
    padding: 5px 10px; /* 원하는 크기로 조정 */
    text-align: center;
    background-color: #3498db; /* 배경색을 원하는 색상으로 변경 */
    color: #ffffff; /* 텍스트 색상을 원하는 색상으로 변경 */
    border: none;
    cursor: pointer;
}
        
    </style>

<script type="text/javascript">

$(function() {
	 
	 //초기 로딩 시 스크롤바의 높이와 문서의 높이를 비교 스크롤 아래 가면 데이터 로딩
/* 	 if($(window).height() == $(document).height()){
		
		 loadMoreDate()
		 
	 }  */
	 //현재 페이지와 무한스크롤 활성화 여부
	 let currentPage = 1;
	 let infiniteScrollEnabled = true;
	 
	 //무한스크롤(스크롤생성 event??)
	 window.addEventListener("scroll", function() {			 
	 
	//문서의 전체높이
	 let scrollHeight = document.documentElement.scrollHeight;
	 
	//window.innerHeight = 핸재보이는 브라우저 창 높이
	//window.scrollY 현재 스크롤 바 위치
	 let scrollPosition = window.innerHeight + window.scrollY;
	console.log(scrollPosition)
	console.log(scrollHeight)
	
	///체크해가면서 window와 document확인
	console.log("cex"+infiniteScrollEnabled)
	 
	//scrollHeight = 문서전체높이  scrollPosition = 스크롤높이
	//scrollHeight = scrollPosition == 최하단
/* 			 if (infiniteScrollEnabled && (scrollHeight - scrollPosition) / scrollHeight === 0) {
		    infiniteScrollEnabled = true; // 중복 요청을 막기 위해 활성화 상태를 비활성화로 변경
		    loadMoreData();
		  }
		}); */
		
    ///scrollHeight - scrollPosition < 10은 테스트용 조건이므로 최소한의 호출을 하도록 수정할것
    //scrollHeight - scrollPosition < 1 변경해도 완려
	 if (infiniteScrollEnabled && scrollHeight - scrollPosition <10) {
		    infiniteScrollEnabled = false; // 중복 요청을 막기 위해 활성화 상태를 비활성화로 변경
		    loadMoreData();
		  }
		});
	
	
	 function loadMoreData() {
		    let searchConditionValue = $('select[name="searchCondition"]').val();
		    let searchKeywordValue = $('input[name="searchKeyword"]').val();
		    let currentPageValue = parseInt($('input[name="currentPage"]').val());
		    currentPageValue++;

		    $('input[name="currentPage"]').val(currentPageValue);

		    $.ajax({
		        url: "/product/json/listProduct",
		        data: JSON.stringify({
		            currentPage: currentPageValue,
		            searchKeyword: searchKeywordValue,
		            searchCondition: searchConditionValue
		        }),
		        method: "POST",
		        contentType: "application/json",
		        dataType: "json",
		        success: function (data, status) {
		            let prodList = data.list;
		            let resultPage = data.resultPage;

		            prodList.forEach(function (product) {
		                // 동적으로 생성되는 행에 .product-row 클래스를 추가하여 스타일 적용
		                let row = "<div class=col-lg-3 col-md-4 col-sm-6 style=margin-bottom: 20px;>"
		                    + "<div class=card shadow-sm style=width: 20rem>"
		                    + "<svg height=50% xmlns=http://www.w3.org/2000/svg role=img aria-label=Image>"
		                    + "<image x=0 y=0 width=100% height=100% xlink:href=/images/uploadFiles/"+product.fileName+" preserveAspectRatio=xMidYMid slice/>"
		                    + "</svg>"
		                    + " <div class=card-body mb-3>"
		                    + "<p class=card-text height=60px>"+product.prodDetail+"</p>"
		                    + "<div class=d-flex justify-content-between align-items-center>"
		                    + "<div class=btn-group data-prodNo="+product.prodNo+">"
		                    +"	<a href= /product/getProduct?prodNo="+product.prodNo+"class=button-link>"
		                    + "<button type=button class=bobotun>구매</button></a>"
		                    +"</div>" 
			      			+"</div>"
			      			+"</div>"
			      			+"</div>"
			      			+"</div>";
		                $('div.row').eq(1).append(row);
		            });

		            infiniteScrollEnabled = true;
 
		        		      		        
		        },
		        
		    });
		}

 
		 $('.bobotun_1').on("click", function(){
		    // "구매" 버튼을 클릭할 때 실행되는 함수
		    var prodNo = $(this).closest(".btn-group").data("prodNo");

		    console.log("prodNo: " + prodNo);
		    self.location = "/product/getProduct?prodNo=" + prodNo;
		});

 
 
 });

</script>   
  </head>
  <body>
  <form name="detailForm" >
  <div style="width:98%; margin-left:10px;">
  <section>
  <input type="hidden" name="currentPage" value="0" />
<header data-bs-theme="dark">
  <div class="navbar navbar-dark bg-dark shadow-sm">
    <div class="container">
      <a href="#" class="navbar-brand d-flex align-items-center">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="50" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/><circle cx="12" cy="13" r="4"/></svg>
        <h2><strong>MVCShop</strong></h2>
      </a>
    </div>
  </div>
</header>



  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <div class="col-lg-6 col-md-8 mx-auto">
        <h1 class="fw-light">상품목록</h1>
      </div>
    </div>
  </section>

  

<div class="row">
    <c:forEach var="product" items="${list}">
        <div class="col-lg-3 col-md-4 col-sm-6" style="margin-bottom: 20px;"> <!-- 상단 여백을 추가 -->
            <div class="card shadow-sm" style="width: 20rem">
                <svg height="50%" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Image">
                    <title>Image</title>
                    <image x="0" y="0" width="100%" height="100%" xlink:href="/images/uploadFiles/${product.fileName}" preserveAspectRatio="xMidYMid slice" />
                </svg>
                <div class="card-body mb-3">
                  <p class="card-text" style="height: 10px">${product.prodDetail}</p>
                    <div class="d-flex justify-content-between justify-content-end">
   					 <div class="btn-group" data-prodNo="${product.prodNo}">        		
       				<a href="/product/getProduct?prodNo=${product.prodNo}" class="button-link">
           			 <button type="button" class="bobotun">구매</button>
        </a>
    </div>
</div>

</div>
            </div>
        </div>
    </c:forEach>
</div>


        

</section>
</div>
</form>
 
    </body>
</html>
