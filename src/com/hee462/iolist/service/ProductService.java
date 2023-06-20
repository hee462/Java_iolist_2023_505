package com.hee462.iolist.service;

import java.util.List;

import com.hee462.iolist.models.ProductDto;

public interface ProductService {
	
	public void insert();
	public void printList();
	public void printList(List<ProductDto> productList);
	
	public void printProduct(ProductDto productDto);
	
	public ProductDto findByPCode();
	public ProductDto findByPName();
	
}
