package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
import com.model2.mvc.service.user.UserService;

//��ǰ���� controller
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
		    
		    
		    // Model �� View ����
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
			
			  
			 
			 // Model �� View ���� ModelAndView modelAndView = new ModelAndView();
			 ModelAndView modelAndView = new ModelAndView();
			 modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
			 modelAndView.addObject("purchase", purchase);
			 
		    
		    return modelAndView;
		}
		
		@RequestMapping(value = "listPurchase")
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
		    
		    if (user.getRole().equals("admin")) {
		        map = purchaseService.getSaleList(search);
		    } else {
		        map = purchaseService.getPurchaseList(search, ((User) session.getAttribute("user")).getUserId().trim());
		    }
		    
		    Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		    System.out.println(resultPage);
		   
		    String menu = request.getParameter("menu");
			System.out.println("MEMU==="+menu);
		    // Model �� View ����
			ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		    modelAndView.addObject("list", map.get("list"));
		    modelAndView.addObject("resultPage", resultPage);
		    modelAndView.addObject("search", search);
//		    modelAndView.addObject("menu", menu);
		    
		    return modelAndView;
		}
























}