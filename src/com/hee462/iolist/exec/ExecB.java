package com.hee462.iolist.exec;

import com.hee462.iolist.service.BuyerService;
import com.hee462.iolist.service.impl.BuyerServiceImplV1;

public class ExecB {
	public static void main(String[] args) {
		BuyerService buyerService = new BuyerServiceImplV1();
		buyerService.printList();
//		buyerService.findById();
//		buyerService.findByBuName();
//		String newId = buyerService.NewBuId();
//		System.out.println(newId);
		
		buyerService.insert();
		
	}
}
