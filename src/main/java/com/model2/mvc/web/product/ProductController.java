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


//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
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
			
			//업로드한 파일이 허용된 크기 이내인지 확인하는 데 사용
			//허용된 크기 이내일 경우 참
			if(request.getContentLength() < fileUpload.getSizeMax()) {
			
				//텍스트 파일을 파싱하거나, 텍스트 기반 데이터를 분할하고 처리
				StringTokenizer token = null;
				
				//HTTP 요청을 분석하고 업로드된 파일을 fileItemList에 저장
				List fileItemList = fileUpload.parseRequest(request);
				
				//이미지 몇개를 업로드 했는지 확인
				int size = fileItemList.size();
				
				for(int i = 0; i<size; i++) {
					
					FileItem fileItem = (FileItem)fileItemList.get(i);
					
					//input type="file"로 안 넘어왔으면 true
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
	                } else { // 파일 형식이면...
	                    if (fileItem.getSize() > 0) { // 파일을 저장하는 if
	                        int idx = fileItem.getName().lastIndexOf("\\");
	                        // getName()은 경로를 다 가져오기 때문에 lastIndexOf로 잘라낸다
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
//	            System.out.println("파일저장경로"+uploadPath);
//	            File uploadDir = new File(uploadPath);
//	            if (!uploadDir.exists()) {
//	                uploadDir.mkdirs(); // 디렉토리가 존재하지 않으면 생성
//	            }
//
//	            try {
//	                String uploadedFilePath = uploadPath + File.separator + originalFileName;
//	                file.transferTo(new File(uploadedFilePath));
//	                // 파일 이름을 리스트에 추가
//	                fileNames.add(originalFileName);
//	                product.setFileName(fileNames.toString().replace("[", "").replace("]", ""));
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	                // 파일 업로드 중 오류 발생 시 예외 처리
//	            }
//	        }
//	    }
					productService.addProduct(product);
		
					// Model 과 View 연결
					model.addAttribute("product", product);
					return "forward:/product/getProduct.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct( @RequestParam int prodNo , Model model ) throws Exception {

		System.out.println("/product/getProduct :GET");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
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
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		System.out.println("map==="+map);
		String menu = request.getParameter("menu");
		System.out.println("MEMU==="+menu);
		
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
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
		// Model 과 View 연결
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
