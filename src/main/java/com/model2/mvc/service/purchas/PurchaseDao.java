package com.model2.mvc.service.purchas;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {
		
	public Purchase findPurchase(int tranNo) throws Exception;
	
	public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception;
	
	public Map<String,Object> getSaleList(Search search) throws Exception;
	
	public Purchase insertPurchase(Purchase purchase) throws Exception;
	
	public Purchase updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Purchase purchase) throws Exception;
	

}
