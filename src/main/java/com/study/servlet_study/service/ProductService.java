package com.study.servlet_study.service;

import com.study.servlet_study.entity.Product;
import com.study.servlet_study.repository.ProductRepository;

public class ProductService {
	private static ProductService instance;
	private ProductRepository productRepository;
	
	private ProductService() {
		productRepository = productRepository.getInstance();
	}
	
	public static ProductService getInstance() {
		if (instance == null) {
			instance = new ProductService();
		}
		return instance;
	}

	public int addProduct(Product product) {
		if (productRepository.isDuplicationProductName(product.getProductName())) {
			return 0;
		}
		return productRepository.saveProduct(product);
	}
	
	public Product getProduct(String username) {
		return productRepository.findProductByProductName(username);
	}
}
