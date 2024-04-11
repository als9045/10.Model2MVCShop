<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.purchas.*" %>
<%@ page import="com.model2.mvc.common.*" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script type="text/javascript">
function fncGetPurchaseList(currentPage) {
	
	$("#currentPage").val(currentPage)

	$("form").attr("method", "POST").attr("action",
			"/purchase/listPurchase").submit()
}

	
	let menu = "${menu}";
	let tranNo ="${purchase.tranNo}";
	console.log("현재 접속자는 "+menu)
	console.log("tranNo "+tranNo)
	

		
		
		$(function() {
		
			$('.ct_list_pop td b:contains("배송하기")').on('click', function() {
		        // 배송 처리할 tranNo 값을 가져옵니다.
		        
		        
		        
		        let tranNo = $(this).data('tranno');
		        console.log("tranNo====: " + tranNo);
		        
		        let updateTranCode = 2;
		        
		        let target = $('.ct_list_pop td b:contains("배송하기")');
		        
		        $.ajax({
		            url: "/purchase/json/updateTranCode/" + tranNo + "/" + updateTranCode,
		            method: "GET",
		            contentType: "application/json",
		            dataType: "json",
		            success: function(data) {
		                target.parent().text("배송중"); 
		                target.remove();
		            },
		        });
		    });
						
			
		$('.ct_list_pop td b:contains("물건도착")').on('click', function() {
        // 배송 처리할 tranNo 값을 가져옵니다.
        
       
        
        let tranNo = $(this).data('tranno');
        console.log("tranNo: " + tranNo);
        
        let updateTranCode = 3;
        
        let target = $(this);
        
        $.ajax({
            url: "/purchase/json/updateTranCode/" + tranNo + "/" + updateTranCode,
            method: "GET",
            contentType: "application/json",
            dataType: "json",
            success: function(data) {
                /* target.parent().text("배송완료"); */
                target.remove();
            },
            
        });
    });

	
	
	
	
	});

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">


		<!-- <form name="detailForm" action="/purchase/listPurchase?menu=${param.menu}" method="post"> -->
		<form name="detailForm" action="/purchase/listPurchase?menu=${menu}" method="post">

			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">${user.role eq 'admin' ? "배송관리" : "구매목록 조회"}</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37"></td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td align="right"><select name="searchCondition"
						class="ct_input_g" style="width: 80px">
							<option value="0"
								${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품명</option>
					</select> <input type="text" name="searchKeyword"
						value="${! empty search.searchKeyword ? search.searchKeyword : '' }"
						class="ct_input_g" style="width: 200px; height: 19px" /></td>
					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;">검색</td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">전체 ${resultPage.totalCount} 건수, 현재
						${resultPage.currentPage} 페이지</td>
				</tr>
				<tr>
						  <td class="ct_list_b" width="50">No</td>
      					  <td class="ct_line02" width="10"></td>
      					   <td class="ct_list_b" width="100">상품사진</td>
        				 <td class="ct_line02" width="10"></td>
       					  <td class="ct_list_b" width="120">상품명<br>
        				  <h7>(상품명 click:상세정보)</h7></td>
        				  <td class="ct_line02" width="10"></td>
        				  <td class="ct_list_b" width="100">주문일</td>
        				  <td class="ct_line02" width="10"></td>
        			   	  <td class="ct_list_b" width="100">배송날짜</td>
        				  <td class="ct_line02" width="10"></td>
        				  <td class="ct_list_b" width="150">배송현황</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				<c:forEach var="purchase" items="${list}">
						<c:set var="i" value="${ i+1 }" />
					<tr class="ct_list_pop">
						<td align="center">${ i }</td>
						<td></td>
						<td align="center"  height="80px">
						<img src="/images/uploadFiles/${purchase.purchaseProd.fileName}" width="100px" height="100px" />
						</td>
						<td></td>
						<td class="ct_list_b" width="150">
						<a href="/purchase/getPurchase?tranNo=${purchase.tranNo}">
							${purchase.purchaseProd.prodName}</a> 
					
						</td>
						<td></td>
						<td align="center">${purchase.orderDate}</td>
						<td></td>
						<td align="center">${purchase.divyDate}</td>
						<td></td>
						<td align="center">
						<c:if test="${fn:trim(purchase.tranCode) eq '1' }">
						    구매완료
						    <c:choose>
						        <c:when test="${menu eq 'manage' }">
						            <b data-tranNo="${purchase.tranNo}">배송하기</b>
						        </c:when>
						        <c:otherwise>
						            <!-- 다른 경우에 대한 처리 -->
						        </c:otherwise>
						    </c:choose>
						</c:if>
						
						<c:if test="${fn:trim(purchase.tranCode) eq '2' }">
						    배송중
						    <c:choose>
						        <c:when test="${menu eq 'search' }">
						            <b data-tranNo="${purchase.tranNo}">물건도착</b>
						        </c:when>
						        <c:otherwise>
						            <!-- 다른 경우에 대한 처리 -->
						        </c:otherwise>
						    </c:choose>
						</c:if>
						
						<c:if test="${fn:trim(purchase.tranCode) eq '3' }">
						    배송완료
						</c:if>
						</td>
					
					</tr>
				</c:forEach>
			</table>
			
		
		
		</form>

	</div>

</body>
</html>