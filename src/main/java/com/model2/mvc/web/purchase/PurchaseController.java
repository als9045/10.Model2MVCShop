package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchas.PurchaseService;
import com.model2.mvc.service.purchase.impl.purchaseDaoImple;
import com.model2.mvc.service.user.UserService;

//상품관리 controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

		
		@Autowired
		@Qualifier("purchaseServiceImpl")
		private PurchaseService purchaseService;
		
		@Autowired
		@Qualifier("userServiceImpl")
		private UserService userService;
		
		@Autowired
		@Qualifier("productServiceImpl")
		private ProductService productService;
		
		public PurchaseController(){
			System.out.println(this.getClass());
		}
		
		@Value("#{commonProperties['pageUnit']}")
		//@Value("#{commonProperties['pageUnit'] ?: 3}")
		int pageUnit;
		
		@Value("#{commonProperties['pageSize']}")
		//@Value("#{commonProperties['pageSize'] ?: 2}")
		int pageSize;
	
		@RequestMapping(value = "addPurchase", method = RequestMethod.GET)
		public ModelAndView addPurchaseView(@RequestParam ("prodNo")int prodNo) throws Exception {
			
			System.out.println("purchase/addPurchaseView");
		    
		    //Business Logic
		    Product product = productService.getProduct(prodNo);
		    
		    
		    // Model 과 View 연결
		    ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		    modelAndView.addObject("product", product);
		    
		    return modelAndView;
		}
		
		@RequestMapping(value = "addPurchase", method = RequestMethod.POST)
		public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase, 
										@RequestParam("prodNo") int prodNo,
										HttpSession session) throws Exception {
			
			 System.out.println("purchase/addPurchase");
			  
			 Product product = productService.getProduct(prodNo);
			 System.out.println("prodNo==="+prodNo); 
			 purchase.setPurchaseProd(product);
			 purchase.setBuyer((User) session.getAttribute("user"));
			 System.out.println(purchase.toString());
			 purchaseService.addPurchase(purchase);
			 
			 System.out.println("Purchase=="+purchase);
			
			  
			 
			 // Model 과 View 연결 ModelAndView modelAndView = new ModelAndView();
			 ModelAndView modelAndView = new ModelAndView();
			 modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
			 modelAndView.addObject("purchase", purchase);
			 
		    
		    return modelAndView;
		}
		
		@RequestMapping(value = "listPurchase", method = RequestMethod.POST)
		public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpServletRequest request) throws Exception {
		    
			System.out.println("/listPurchase");
			
		    HttpSession session = request.getSession(true);
		    
		   
		    if (search.getCurrentPage() == 0) {
		        search.setCurrentPage(1);
		    }
		    search.setPageSize(pageSize);
		    
		    //Business Logic
		    Map<String, Object> map = null;
		    
		    User user = userService.getUser(((User) session.getAttribute("user")).getUserId());
		    System.out.println("user==="+user);
		    if (user.getRole().equals("admin")) {
		        map = purchaseService.getSaleList(search);
		    } else {
		        map = purchaseService.getPurchaseList(search, ((User) session.getAttribute("user")).getUserId().trim());
		    }
		    
		    Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		    System.out.println(resultPage);
		    
		    String menu = request.getParameter("menu");
			System.out.println("여기까지=====");
		    // Model 과 View 연결
			ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		    modelAndView.addObject("list", map.get("list"));
		    modelAndView.addObject("resultPage", resultPage);
		    modelAndView.addObject("search", search);
		    modelAndView.addObject("menu", menu);
		   
		    return modelAndView;
		}
		@RequestMapping(value = "listPurchase", method = RequestMethod.GET)
		public ModelAndView listPurchase_1(@ModelAttribute("search") Search search, HttpServletRequest request) throws Exception {
		    
			System.out.println("/listPurchase");
			
		    HttpSession session = request.getSession(true);
		    
		   
		    if (search.getCurrentPage() == 0) {
		        search.setCurrentPage(1);
		    }
		    search.setPageSize(pageSize);
		    
		    //Business Logic
		    Map<String, Object> map = null;
		    
		    User user = userService.getUser(((User) session.getAttribute("user")).getUserId());
		    
		    if (user.getRole().equals("admin")) {
		        map = purchaseService.getSaleList(search);
		    } else {
		        map = purchaseService.getPurchaseList(search, ((User) session.getAttribute("user")).getUserId().trim());
		    }
		    
		    Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		    System.out.println(resultPage);
		    
		    String menu = request.getParameter("menu");
			System.out.println("MEMU==="+menu);
		    // Model 과 View 연결
			ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		    modelAndView.addObject("list", map.get("list"));
		    modelAndView.addObject("resultPage", resultPage);
		    modelAndView.addObject("search", search);
		    modelAndView.addObject("menu", menu);
		   
		    return modelAndView;
		}
		@RequestMapping(value ="getPurchase")
		public ModelAndView getPurchase (@RequestParam("tranNo") int tranNo)throws Exception {
			
			System.out.println("purchase/getPurchase");
		
			Purchase purchase1  =  purchaseService.getPurchase(tranNo);
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
			modelAndView.addObject("purchase", purchase1);
		
			
			return modelAndView;
}
				
		@RequestMapping(value="updatePurchaseView", method = RequestMethod.GET)
		public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo) throws Exception {
			
			System.out.println("purchas/updatePurchaseView");
			
			Product product = productService.getProduct(tranNo);
			Purchase purchase =purchaseService.getPurchase(tranNo);
		
						
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
			modelAndView.addObject("purchase",purchase);
			return modelAndView;
			
		}

		@RequestMapping(value="updatePurchase", method = RequestMethod.POST)
		public ModelAndView updatePurchase(@ModelAttribute("purchse") Purchase purchase,
											@RequestParam("tranNo") int tranNo) throws Exception{
			
			System.out.println("purchase/updatePurchase");
			
			Purchase getpurchase = purchaseService.getPurchase(tranNo);
			
			purchase.setBuyer(getpurchase.getBuyer());
			purchase.setPurchaseProd(getpurchase.getPurchaseProd());
			purchase.setOrderDate(getpurchase.getOrderDate());
			purchase = purchaseService.updatePurchase(purchase);
			
			ModelAndView modelAndView = new ModelAndView();
			 modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
			modelAndView.addObject("purchase", purchase);
			return modelAndView;
			
		}

	

















}