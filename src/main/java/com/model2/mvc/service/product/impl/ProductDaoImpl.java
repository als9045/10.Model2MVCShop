package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;


//==> 회원관리 DAO CRUD 구현
@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//Constructor
	public  ProductDaoImpl() {
		System.out.println(this.getClass()+"===생성자==");
	}
	
	@Override
	public void addProduct(Product product) throws Exception {
		sqlSession.insert("ProductMapper.addProduct",product);
		System.out.println("addDAO=============================");
		
	}
	@Override
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("DAOIMPLE=============================");
		return sqlSession.selectOne("ProductMapper.getProduct",prodNo);
	}
	@Override
	public List<Product> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ProductMapper.getProductList", search);
	}
	@Override
	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct", product);
		
	}
	public void updateProductCount(Map<String, Object> purchase) throws Exception{
		System.out.println("DAOimpl에서 purchase는:   "+purchase );
		sqlSession.update("ProductMapper.updateProductCount",purchase);
	}
	
	@Override
	public int getTotalCount(Search search) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

}
	
	