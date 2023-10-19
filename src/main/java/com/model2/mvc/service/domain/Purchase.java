package com.model2.mvc.service.domain;

import java.sql.Date;

public class Purchase {


	///Field
	private int rowNum;
	private User buyer;//회원 아이디
	private String divyAddr;//배송 주소
	private String divyDate;//배송희망일
	private String divyRequest;//배송요청사항
	private Date orderDate;//주문일시
	private String paymentOption;//결제 방법
	private Product purchaseProd;//
	private String receiverName;//수령인
	private String receiverPhone;//수령인 번호
	private String tranCode;//코드
	private int tranNo;//번호
	private int prodCount;	
	
	public Purchase() {
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public String getDivyAddr() {
		return divyAddr;
	}

	public void setDivyAddr(String divyAddr) {
		this.divyAddr = divyAddr;
	}

	public String getDivyDate() {
		return divyDate;
	}

	public void setDivyDate(String divyDate) {
		this.divyDate = divyDate.replace("-","");
	}

	public String getDivyRequest() {
		return divyRequest;
	}

	public void setDivyRequest(String divyRequest) {
		this.divyRequest = divyRequest;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}

	public Product getPurchaseProd() {
		return purchaseProd;
	}

	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public int getProdCount() {
		return prodCount;
	}

	public void setProdCount(int prodCount) {
		this.prodCount = prodCount;
	}

	@Override
	public String toString() {
		return "Purchase [rowNum=" + rowNum + ", buyer=" + buyer + ", divyAddr=" + divyAddr + ", divyDate=" + divyDate
				+ ", divyRequest=" + divyRequest + ", orderDate=" + orderDate + ", paymentOption=" + paymentOption
				+ ", purchaseProd=" + purchaseProd + ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", tranCode=" + tranCode + ", tranNo=" + tranNo + ", prodCount=" + prodCount + "]";
	}
	



}
	

