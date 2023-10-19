<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.purchas.*" %>
<%@ page import="com.model2.mvc.common.*" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
	
	let menu = "${menu}";
	console.log("���� �����ڴ� "+menu)
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">


		<!-- <form name="detailForm" action="/purchase/listPurchase?menu=${param.menu}" method="post"> -->
		<form name="detailForm">
		<input type="hidden" name="currentPage" value="1" />
			<table width="100%" height="37" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
						width="15" height="37"></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"
						style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">${user.role eq 'admin' ? "��۰���" : "���Ÿ�� ��ȸ"}</td>
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
								${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��</option>
					</select> <input type="text" name="searchKeyword"
						value="${! empty search.searchKeyword ? search.searchKeyword : '' }"
						class="ct_input_g" style="width: 200px; height: 19px" /></td>
					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23"></td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;">�˻�</td>
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
					<td colspan="11">��ü ${resultPage.totalCount} �Ǽ�, ����
						${resultPage.currentPage} ������</td>
				</tr>
				<tr>
						  <td class="ct_list_b" width="50">No</td>
      					  <td class="ct_line02" width="10"></td>
       					  <td class="ct_list_b" width="250">��ǰ��<br>
        				  <h7>(��ǰ�� click:������)</h7></td>
        				  <td class="ct_line02" width="10"></td>
        				  <td class="ct_list_b" width="100">�ֹ���</td>
        				  <td class="ct_line02" width="10"></td>
        			   	  <td class="ct_list_b" width="100">��۳�¥</td>
        				  <td class="ct_line02" width="10"></td>
        				  <td class="ct_list_b" width="250">�����Ȳ</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				<c:forEach var="purchase" items="${list}">
					<tr class="ct_list_pop">
						<td align="center"  height="80px">
						<img src="/images/uploadFiles/${purchase.purchaseProd.fileName}" width="100px" height="100px" />
						</td>
						<td></td>
						<td class="ct_list_b" width="150">
						<a <%-- href="/purchase/getPurchase?tranNo=${purchase.tranNo}" --%>>
							${purchase.purchaseProd.prodName}</a>
						</td>
						<td></td>
						<td align="left">${purchase.orderDate}</td>
						<td></td>
						<td align="left">${purchase.divyDate}</td>
						<td></td>
						<td align="left">
						<c:if test="${fn:trim(purchase.tranCode) eq '1' }">
						    ���ſϷ�
						    <c:choose>
						        <c:when test="${menu eq 'manage' }">
						            <b data-tranNo="${purchase.tranNo}">����ϱ�</b>
						        </c:when>
						        <c:otherwise>
						            <!-- �ٸ� ��쿡 ���� ó�� -->
						        </c:otherwise>
						    </c:choose>
						</c:if>
						
						<c:if test="${fn:trim(purchase.tranCode) eq '2' }">
						    �����
						    <c:choose>
						        <c:when test="${menu eq 'search' }">
						            <b data-tranNo="${purchase.tranNo}">���ǵ���</b>
						        </c:when>
						        <c:otherwise>
						            <!-- �ٸ� ��쿡 ���� ó�� -->
						        </c:otherwise>
						    </c:choose>
						</c:if>
						
						<c:set var="trim_trancod" value="${fn:trim(purchase.tranCode)}"/>
						<c:if test="trim_trancod == '3' ">
						    ��ۿϷ�
						</c:if>
						</td>
					
					</tr>
				</c:forEach>
			</table>
		</form>

	</div>

</body>
</html>