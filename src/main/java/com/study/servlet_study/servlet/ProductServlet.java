package com.study.servlet_study.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet_study.entity.Product;
import com.study.servlet_study.service.ProductService;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;
       
    public ProductServlet() {
        super();
    	productService = productService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productName = request.getParameter("productName");
		
		Product product = productService.getProduct(productName);
		if (product != null) {
			response.setStatus(200);
			response.getWriter().println(product);
		} else if (product == null) {
			response.setStatus(400);
			response.getWriter().println("검색된 물품이 없습니다.");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int price = 0;
		
		try {
			price = Integer.parseInt(request.getParameter("price"));
		} catch (NumberFormatException e) {
			response.setStatus(400);
			response.getWriter().println("숫자만 입력해야 합니다.");
			return;
		}
		
		Product product = Product.builder()
				.productName(request.getParameter("productName"))
				.price(price)
				.size(request.getParameter("size"))
				.color(request.getParameter("color"))
				.build();
				
		if(productService.getProduct(product.getProductName()) != null) {
			response.setStatus(400);
			response.getWriter().println("이미 등록된 물품입니다.");
			return;
		}
		
		productService.addProduct(product);
		response.setStatus(201);
		response.getWriter().println("정상적으로 상품등록되었습니다.");

			
		
	}

}
