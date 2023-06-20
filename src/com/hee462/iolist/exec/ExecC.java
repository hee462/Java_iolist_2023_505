package com.hee462.iolist.exec;

import com.hee462.iolist.service.BuyerService;
import com.hee462.iolist.service.impl.BuyerServiceImplV2;

public class ExecC {
	public static void main(String[] args) {
		BuyerService buyerService = new BuyerServiceImplV2();
//		buyerService.findById();
		buyerService.insert();
		buyerService.printList();
	}
}	
