package com.model2.mvc.web.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> 회원관리 Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductRestController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	
	@RequestMapping(value = "json/addProduct" , method = RequestMethod.POST)
	public Product addProduct( @RequestBody Product product ) throws Exception {

		System.out.println("/product/addProduct :Post");
		//Business Logic
		productService.addProduct(product);
		
		return productService.getProduct(product.getProdNo());
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value = "json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo) throws Exception {
		/*
		 * System.out.println("No"+No); int prodNo = Integer.parseInt(No);
		 * System.out.println("prodNo"+prodNo);
		 */		System.out.println("/product/json/getProduct : GET");
		//Business Logic

		// Model 과 View 연결
		
		return   productService.getProduct(prodNo);
	}
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value = "json/listProduct")
	public Map <String , Object> listProduct(@RequestBody Search search , Model model , HttpServletRequest request) throws Exception{

		System.out.println("/json/listProduct ");
		//Business Logic
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}else {
			
			search.setCurrentPage(search.getCurrentPage());
			
		}
			search.setPageSize(pageSize);
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		System.out.println("map ==" +map);
		String menu = request.getParameter("menu");
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		List<String> resultList = new ArrayList<>();
		List<Product> productList = (List<Product>) map.get("list");
		 
		// AutoComplete추가 코드
		for (Product product : productList) {
			 if ("0".equals(search.getSearchCondition())) {
		            // searchCondition이 0인 경우 userId를 리스트에 추가
		            resultList.add(""+product.getProdNo());
		        } else if ("1".equals(search.getSearchCondition())) {
		            // searchCondition이 1인 경우 userName을 리스트에 추가
		            resultList.add(product.getProdName());
		        }
		}
		
		map.put("resultList", resultList);
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);
		map.put("search", search);
	
		return map;
	}
		
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value = "json/updateProduct", method = RequestMethod.POST)
	public Product updateProduct( @RequestBody Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		productService.updateProduct(product);
		
		
		return product;
	}

	}
