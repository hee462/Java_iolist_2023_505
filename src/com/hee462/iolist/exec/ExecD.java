package com.hee462.iolist.exec;

import com.hee462.iolist.service.ProductService;
import com.hee462.iolist.service.impl.ProductServiceImplV1;

public class ExecD {
	public static void main(String[] args) {
		ProductService productService = new ProductServiceImplV1();
		productService.insert();
	}
}
