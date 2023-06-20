package com.hee462.iolist.service;

import java.util.List;

import com.hee462.iolist.models.BuyerDto;
/*
 * method 를 관리하기 용이하게 잘게 자름
 * 설계하기 따라서 맞춰서 작업하면됨 
 * 
 * */
public interface BuyerService {
	// 키보드에서 고개정보를 입력받고 Dao 통해 Insert 하기
	public void insert();
	
	// 키보드에서 고객정보를 입력받고 Dao 통해 Update 하기
	public void update();
	
	// Dao 통해 SelectAll 한 리스트를 Console 출력하기
	public void printList();
	
	// 매개변수를 통하여 리스트를 전달받고 Console에 출력하기
	public void printList(List<BuyerDto> buyerList);
	
	// 한 사람의 고객정보를 출력하기
	// Dto 정보를 매개변수를 통하여 전달받고 출력하기
	public void printBuyer(BuyerDto buyerDto);
	
	// 고객정보 조회하기
	// ID 조회하기, 이름으로 조회하기, 전화번호로 조회하기
	// 출력은?
	
	// 고객ID를 입력받고, 찾은 고객정보를 return 하기
	public BuyerDto findById();
	
	
	
	public BuyerDto findById(String buId);
	
	/*
	 * 고객이름을 입력받고 리스트를 보여주고 고객 ID를 선택하고
	 * 찾은 고객정보를 return 하기
	 * 
	 */
	public BuyerDto findByBuName();
	// 전화번호로 조회하기
	public BuyerDto findByBuTel();
	// 고객ID 자동으로 새로 생성하기 ->고객 ID 입력햇는데 없으면!!
	public String NewBuId();
	
	
	
	
}
