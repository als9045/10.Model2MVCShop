<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>상품목록조회</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<!-- jQuery UI CDN(Content Delivery Network) 호스트 사용 -->
<!-- CDN(Content Delivery Network) 호스트 사용 -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
	
		function fncGetProductList(currentPage) {
			$("#currentPage").val(currentPage)
		   	//document.detailForm.submit();
			$("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
		}
		//===========================================//
		//==> 추가된부분 : "검색" ,  userId link  Event 연결 및 처리
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
			 if (infiniteScrollEnabled && scrollHeight - scrollPosition < 1) {
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
				                let row = "<tr class='ct_list_pop'>"
				                    + "<td align=center width=100>" + product.prodName + "</td>"
				                    + "<td></td>"
				                    + "<td align=center width=100>" + product.prodNo + "</td>"
				                    + "<a href='/product/";

				                if ("${param.menu}" === 'manage') {
				                    row += "updateProduct?prodNo=" + product.prodNo + "&menu=manage";
				                } else if ("${param.menu}" === 'search') {
				                    row += "getProduct?prodNo=" + product.prodNo + "&menu=search";
				                }

				                row += "'/>"
				                    + "<td></td>"
				                    + "<td align=center>" + product.price + "</td>"
				                    + "<td></td>"
				                    + "<td align=center width=100>" + product.regDate + "</td>"
				                    + "<td></td>"
				                    + "</tr>";

				                $('.cex').append(row);
				            });

				            infiniteScrollEnabled = true;
				        },
				    });
				}

			 
			 
			 
			 
			 
			//==> 검색 Event 연결처리부분
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			//==> 1 과 3 방법 조합 : $("tagName.className:filter함수") 사용함. 
			
			$( "td.ct_btn01:contains('검색')" ).on("click" , function() {
				//Debug..
				//alert(  $( "td.ct_btn01:contains('검색')" ).html() );
				fncGetProductList(1);
			});
			
			 $( ".ct_list_pop td:nth-child(3)" ).on("click" , function() {
					
					
					
					var name =  $(this).closest(".ct_list_pop");
					var prodNo = name.find("td:nth-child(5)").text().trim();
					//alert("값 확인: " + prodNo);
					
					var menu = "${menu}";
					//alert("값 확인: " + menu);
					if(menu ==='manage'){
						self.location = "/product/updateProduct?prodNo=" +prodNo;
					}else{
					
			          self.location = "/product/getProduct?prodNo=" +prodNo;
			      
					}
				});
			//==> userId LINK Event 연결처리
			//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
			//==> 3 과 1 방법 조합 : $(".className tagName:filter함수") 사용함.
			
			/* $( ".ct_list_pop td:nth-child(3)" ).on("click" , function() {
					
				
			
				var name =  $(this).closest(".ct_list_pop");
				var prodNo = name.find("td:nth-child(5)").text().trim();
				
				
				var menu = "${menu}";
				
				if(menu ==='manage'){
					self.location = "/product/updateProduct?prodNo=" +prodNo;
				}else if (menu ==='search'){
					
					var prodName = $(this).text().trim();
					
					$.ajax({
								url : "/product/json/getProduct/"+prodNo ,
								method : "GET" ,
								dataType : "json" ,
								headers : {
									"Accept" : "application/json",
									"Content-Type" : "application/json"
								},
								success : function(JSONData , status) {

									//Debug...
									
									//Debug...
									
									var displayValue = "<h3>"
																+"삼품명: "+JSONData.prodName+"<br/>"
																+"상세정보 : "+JSONData.prodDetail+"<br/>"
																+"제조일자 : "+JSONData.regDate+"<br/>"
																+"가 격 : "+JSONData.price+"<br/>"
																//+"사 진 : "+JSONData.fileName+"<br/>"
																//+"사 진 : "+load('JSONData.fileName')+"<br/>"
																+"</h3>";
									//Debug...									
									
									$("h3").remove();
									$( "#"+prodName+"" ).html(displayValue);
								}
						});
						////////////////////////////////////////////////////////////////////////////////////////////
				}	
			}); */
		 
			//==> UI 수정 추가부분  :  userId LINK Event End User 에게 보일수 있도록 
			
			$("h7").css("color" , "red");
			
			
			//==> 아래와 같이 정의한 이유는 ??
			//==> 아래의 주석을 하나씩 풀어 가며 이해하세요.					
			//$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
			console.log ( $(".ct_list_pop:nth-child(1)" ).html() );
			//console.log ( $(".ct_list_pop:nth-child(2)" ).html() );
			//console.log ( $(".ct_list_pop:nth-child(3)" ).html() );
			//console.log ( $(".ct_list_pop:nth-child(4)" ).html() ); //==> ok
			//console.log ( $(".ct_list_pop:nth-child(5)" ).html() ); 
			//console.log ( $(".ct_list_pop:nth-child(6)" ).html() ); //==> ok
			//console.log ( $(".ct_list_pop:nth-child(7)" ).html() ); 
		});	

</script>



</head>
<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">
<input type="hidden" name="currentPage" value="1" />
<input type="hidden" name="proNo" value="${product.prodNo}" />
<form name="detailForm" action="/product/listProduct?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					
					<td width="93%" class="ct_ttl01">
					${menu eq 'manage' ? "상품관리" : "상품목록조회"}	
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
		
				<option value="0" ${ ! empty sera.search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${ ! empty sera.search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품명</option>
				
		</select>
			<input type="text" name="searchKeyword"  value="${! empty search.searchKeyword ? search.searchKeyword : '' }"
			class="ct_input_g" style="width:200px; height:19px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						검색
						<!-- <a href="javascript:fncGetproductList(1);">검색</a> -->
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<!-- id는 테스트 후 삭제할것. 삭제하는 이유는 비종속 -->
<table class="cex" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11"  >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">이미지</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="250">가격</td>	
		<td class="ct_line02" ></td>
		<td class="ct_list_b" width="700">등록일</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="center"width="100">${product.prodName}</td>
			<td></td>
			<td align="center"width="100">${product.prodNo}</td>
			<td></td>
			<td align="center">${product.price}
			<td></td>
			<td align="center"width="100">
			<img src="/images/uploadFiles/${product.fileName}" width="100px" height="100px"/>
			</td>
			<td></td>		
		</tr>
		<tr>
		<td id="${product.prodName}"colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

		
	


<%-- <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>

    		<jsp:include page="../common/pageNavigator.jsp">
    				<jsp:param name="type" value="Product"/>
    		</jsp:include>	
    	</td>
	</tr>
</table> --%>
<!--  페이지 Navigator 끝 -->

</form>
</div>

</body>
</html>
