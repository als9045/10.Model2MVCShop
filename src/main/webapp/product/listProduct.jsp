<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
<html>
<head>

<title>상품목록조회</title>
<link rel="stylesheet" href="/css/admin.css" type="text/css">


<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
//검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
/* <!--
function fncGetproductList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
--> */
</script>
</head>
<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
				<c:choose>
					<c:when test = "${'manage' eq menu}">
					<td width="93%" class="ct_ttl01">상품 목록조회
					</c:when>
					<c:when test = "${'search' eq menu}">
					<td width="93%" class="ct_ttl01">상품검색
					</c:when>
				</c:choose>
				
					
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
			<input type="text" name="searchKeyword"  value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  
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


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11"  >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품번호</td>
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
			<td align="center"width="100">${product.regDate}</td>
			<td></td>		
		</tr>
		<tr>
		<td id="${product.prodName}"colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>

		
	


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>

    		<jsp:include page="../common/pageNavigator.jsp">
    				<jsp:param name="type" value="Product"/>
    		</jsp:include>	
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>
</div>

</body>
</html>
