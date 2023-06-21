package com.hee462.iolist.exec;

import com.hee462.iolist.config.HelpMessage;
import com.hee462.iolist.controller.MainController;

public class ExecF {
	public static void main(String[] args) {
		MainController mainController = new MainController();
		mainController.startApp();
		HelpMessage.OK("야호 퇴근이다.....");
	}
}
