package com.model2.mvc.web.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchas.PurchaseService;
import com.model2.mvc.service.user.UserService;

//상품관리 controller
@RestController
@RequestMapping("/purchase/*")
public class RestPurchaseController {

		
		@Autowired
		@Qualifier("purchaseServiceImpl")
		private PurchaseService purchaseService;
		
		@Autowired
		@Qualifier("userServiceImpl")
		private UserService userService;
		
		@Autowired
		@Qualifier("productServiceImpl")
		private ProductService productService;
		
		public RestPurchaseController(){
			System.out.println(this.getClass());
		}
		
		@Value("#{commonProperties['pageUnit']}")
		//@Value("#{commonProperties['pageUnit'] ?: 3}")
		int pageUnit;
		
		@Value("#{commonProperties['pageSize']}")
		//@Value("#{commonProperties['pageSize'] ?: 2}")
		int pageSize;
	
		

		@RequestMapping(value = "json/updateTranCode/{tranNo}/{updateTranCode}", method = RequestMethod.GET)
		public void updateTranCode(@PathVariable("tranNo") int tranNo,
									@PathVariable("updateTranCode") String updateTranCode) throws Exception {
			

			System.out.println("purchase/json/updateTranCode");
		
			Purchase purchase = purchaseService.getPurchase(tranNo);
			purchase.setTranCode(updateTranCode);
			purchase.setTranNo(tranNo);
			
			purchaseService.updateTranCode(purchase);
			System.out.println("ajax"+purchase);
		}




}
















