package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method ���� ����
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	//@RequestMapping("/addProductView.do")
	//public String addProductView() throws Exception {
	@RequestMapping( value = "addProduct", method = RequestMethod.GET)
	public String addProduct( @RequestParam("prodNo") int prodNo, Model model) throws Exception{
		System.out.println("/product/addProduct :GET");
		
		Product product = productService.getProduct(prodNo);
		model.addAttribute("product", product);
		
		return "forward:/product/readProduct.jsp";
		/*
		 * ModelAndView modelAndView = new ModelAndView();
		 * modelAndView.setViewName("forward:/product/readProduct.jsp");
		 * modelAndView.addObject("product", product);
		 * 
		 * return modelAndView;
		 */
		
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping(value = "addProduct" , method = RequestMethod.POST)
	public String addProduct( @ModelAttribute("product") Product product, Model model,
								@RequestParam("file") MultipartFile[] files,
								HttpServletRequest request) throws Exception {
		System.out.println(product);
		if(FileUpload.isMultipartContent(request)) {
			
			String temDir ="C:\\workspace\\10.Model2MVCShop(Ajax) (1)\\src\\main\\webapp\\images\\uploadFiles\\";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024 * 1024 * 10);
			fileUpload.setSizeThreshold(1024 * 100);
			
			//���ε��� ������ ���� ũ�� �̳����� Ȯ���ϴ� �� ���
			//���� ũ�� �̳��� ��� ��
			if(request.getContentLength() < fileUpload.getSizeMax()) {
			
				//�ؽ�Ʈ ������ �Ľ��ϰų�, �ؽ�Ʈ ��� �����͸� �����ϰ� ó��
				StringTokenizer token = null;
				
				//HTTP ��û�� �м��ϰ� ���ε�� ������ fileItemList�� ����
				List fileItemList = fileUpload.parseRequest(request);
				
				//�̹��� ��� ���ε� �ߴ��� Ȯ��
				int size = fileItemList.size();
				
				for(int i = 0; i<size; i++) {
					
					FileItem fileItem = (FileItem)fileItemList.get(i);
					
					//input type="file"�� �� �Ѿ������ true
	                if (fileItem.isFormField()) {
	                    if (fileItem.getFieldName().equals("manuDate")) {
	                        token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
	                        String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
	                        product.setManuDate(manuDate);
	                    } else if (fileItem.getFieldName().equals("prodName"))
	                        product.setProdName(fileItem.getString("euc-kr"));
	                    else if (fileItem.getFieldName().equals("prodDetail"))
	                        product.setProdDetail(fileItem.getString("euc-kr"));
	                    else if (fileItem.getFieldName().equals("price"))
	                        product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
	                } else { // ���� �����̸�...
	                    if (fileItem.getSize() > 0) { // ������ �����ϴ� if
	                        int idx = fileItem.getName().lastIndexOf("\\");
	                        // getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
	                        if (idx == -1) {
	                            idx = fileItem.getName().lastIndexOf("/");
	                        }
	                        String fileName = fileItem.getName().substring(idx + 1);
	                        product.setFileName(fileName);
	                        try {
	                            File uploadedFile = new File(temDir, fileName);
	                            fileItem.write(uploadedFile);
	                           
	                        } catch (IOException e) {
	                            System.out.println(e);
	                        }
	                    } else {
	                        product.setFileName("../../images/empty.GIF");
	                    }
				}
		}
	}
		}
//		List<String> fileNames = new ArrayList<>();
//		for (MultipartFile file : files) {
//	        if (!file.isEmpty()) {
//	            String originalFileName = file.getOriginalFilename();
//	            String uploadPath = request.getServletContext().getRealPath("/images/uploadFiles/");
//	            //String uploadPath = "C:\\Users\\bitcamp\\git\\model2MVCShop\\07.Model2MVCShop(URI,pattern)\\src\\main\\webapp\\images\\uploadFiles";
//	            System.out.println("����������"+uploadPath);
//	            File uploadDir = new File(uploadPath);
//	            if (!uploadDir.exists()) {
//	                uploadDir.mkdirs(); // ���丮�� �������� ������ ����
//	            }
//
//	            try {
//	                String uploadedFilePath = uploadPath + File.separator + originalFileName;
//	                file.transferTo(new File(uploadedFilePath));
//	                // ���� �̸��� ����Ʈ�� �߰�
//	                fileNames.add(originalFileName);
//	                product.setFileName(fileNames.toString().replace("[", "").replace("]", ""));
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	                // ���� ���ε� �� ���� �߻� �� ���� ó��
//	            }
//	        }
//	    }
					productService.addProduct(product);
		
					// Model �� View ����
					model.addAttribute("product", product);
					return "forward:/product/getProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct( @RequestParam int prodNo , Model model ) throws Exception {

		System.out.println("/product/getProduct :GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return  "forward:/product/getProduct.jsp";
	}
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value = "listProduct")
	public String listProduct(  @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{

		System.out.println("/product/listProduct ");
		//Business Logic
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map=productService.getProductList(search);
		
		System.out.println("map==="+map);
		String menu = request.getParameter("menu");
		System.out.println("MEMU==="+menu);
		
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		//model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu" , menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping(value ="updateProduct", method = RequestMethod.GET)
	public String updateProductView( @RequestParam("prodNo")int prodNo , Model model  ) throws Exception{

		System.out.println("/product/updateProduct : GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model �� View ����
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/product/updateProduct : POST");
		//Business Logic
		
		productService.updateProduct(product);
		

		
		return "forward:/product/getProduct.jsp";
	}

	}
