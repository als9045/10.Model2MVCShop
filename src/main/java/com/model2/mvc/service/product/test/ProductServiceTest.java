package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
															"classpath:config/context-aspect.xml",
															"classpath:config/context-mybatis.xml",
															"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//Autowired => 아래 선언한 private bean을 찾아 field에 주입
	//Qualifier("") => 동일한 타입의 bean이 존재할때 어떤 bean을 주입할지 명시적으로 지정 
	//여러 빈 중에서 어떤 빈을 주입할지를 더 명확하게 지정
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		
		product.setProdName("testProduct");
		product.setProdDetail("testdetail");
		product.setProdNo(1234);
		product.setPrice(13);
		product.setManuDate("testmanu");
		
		productService.addProduct(product);
		System.out.println("product===="+product);
		
		product = productService.getProduct(1234);
		
		//ex) Assert.assertEquals("a",a); => a라는 문자와 a라는 data type속 값이 같은지 확인 
		//기대값과 실제값을 비교하고, 만약 이 값들이 같지 않으면 오류 발생
		Assert.assertEquals("testProduct", product.getProdName());
		Assert.assertEquals("testdetail", product.getProdDetail());
		Assert.assertEquals(1234, product.getProdNo());
		Assert.assertEquals(13, product.getPrice());
		Assert.assertEquals("testmanu", product.getManuDate());
		
	}

	//@Test
	public void testGetProduct() throws Exception{
		
		Product product = new Product();
		
		product = productService.getProduct(1234);
	
		Assert.assertEquals("testProduct", product.getProdName());
		Assert.assertEquals("testdetail", product.getProdDetail());
		Assert.assertEquals(1234, product.getProdNo());
		Assert.assertEquals(13, product.getPrice());
		Assert.assertEquals("testmanu", product.getManuDate());
	
	}
	
	 //@Test
	public void testUpdateProduct() throws Exception{
		
		Product product = productService.getProduct(1234);
		Assert.assertNotNull(product);
	
		Assert.assertEquals("testProduct", product.getProdName());
		Assert.assertEquals(13, product.getPrice());
		Assert.assertEquals("testdetail", product.getProdDetail());
	
		product.setProdName("gogogo");
		product.setPrice(11111);
		product.setProdDetail("lalalala");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(1234);
		Assert.assertNotNull(product);
		System.out.println("update====="+product);
	
		Assert.assertEquals("gogogo", product.getProdName());
		Assert.assertEquals(11111, product.getPrice());
		Assert.assertEquals("lalalala", product.getProdDetail());
	}
	
	 //@Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	//@Test
	 public void testGetProductListByProductName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("보르도");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetProductListByProducDetail() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("노트북");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 
}