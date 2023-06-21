package com.hee462.iolist.exec;

import com.hee462.iolist.service.IolistService;
import com.hee462.iolist.service.impl.IolistServiceImplV1;

public class ExecE {
	public static void main(String[] args) {
		IolistService iolistService = new IolistServiceImplV1();
		iolistService.input();
		iolistService.printList();
	}
}
