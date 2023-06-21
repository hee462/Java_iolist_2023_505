package com.hee462.iolist.service.impl;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.hee462.iolist.config.AnsiConsol;
import com.hee462.iolist.config.DBConnection;
import com.hee462.iolist.config.HelpMessage;
import com.hee462.iolist.config.Line;
import com.hee462.iolist.models.BuyerDto;
import com.hee462.iolist.persistance.BuyerDao;
import com.hee462.iolist.service.BuyerService;

/*
 * 객체의 선언과 초기화
 * 객체의 선언 : 클래스를 사용하여 객체(변수)를 선언하는 것
 * 		BuyerDao buyerDao;
 * 객체의 초기화 : 선언된 객체를 통하여 변수, method 에 접근할수 있도록 하는 조치
 * 		BuyerDao buyerDao = new BuyerDao(); -> 생성자로 객체 초기화
 * 
 * 객체의 생성 : 선언되고 초기화 까지 완성된 객체, 사용할 준비가 된 상태
 */
public class BuyerServiceImplV1 implements BuyerService{
	// Service에서 Dao의 method를 사용하기위한 객체 선언
	// 클래스 영역에 선언된 객체는 final 키워드를 필수적으로 사용하자
	// final 키워드를 사용하여 선언된 객체는 만약 초기화가 되지 않으면 
	// 문법 오류를 보여 반드시 초기화작업을 해야한다 -> final 로 선언하면 현재클래스에서 생성자 초기화 작업 필수
	protected  BuyerDao buyerDao; // ByerDao 초기화 작업 ㄴㄴ 
	protected  Scanner scan;
	
	
	public BuyerServiceImplV1() {
		// new 생성자를 사용하지않고 초기화 진행
		//SqlSession의 getMappter() method 통하여 BuyerDao 객체를 전달 받아 초기화한다
		SqlSession session = DBConnection.getSessionFactory().openSession(true);
		buyerDao = session.getMapper(BuyerDao.class);
		scan = new Scanner(System.in);
		

	}

	
	public void insert() {
		BuyerDto buyerDto = null;
		while(true) {
			System.out.println(Line.dLine(70));
			System.out.println("추가할 고객정보를 확인");
			System.out.println("고객ID를 입력하세요 .(QUIT:종료, NEW :ID 생성)");
			System.out.println("고객 ID  >> ");
			String strBuId = scan.nextLine();
			
			if(strBuId.equals("QUIT")) return;
			if(strBuId.equals("NEW")) {
				strBuId = this.NewBuId();
				System.out.println("새로 생성된 ID : " +strBuId);
			}
			// 고객ID strBuId 에 담겨있는데 
			// 키보드로 입력한 ID이거나, 새로 생성한 ID일 것이다.
			// strBuId 에 담긴 고객ID를 findByID()에게 전달
			buyerDto = this.findById(strBuId);
			// 조회된 고객정보가 null 이면 새로운 고객정보를 저장할 Dto를 만들고 고객ID setting
			if(buyerDto == null) {
				buyerDto = new BuyerDto();
				buyerDto.buId = strBuId;
			}
			//기존 고객이 있다면 
			break;
		}
		// 새로운 고객이면 buyerDto는 null
		// 이미 등록된 고객이면 등록된 고객정보를 보여준다.
		System.out.println("고객정보 " + buyerDto);
		while(true) {
			String prompt = buyerDto.buName == null? "새로운 고객명   >>  "
												: String.format("고객명 수정 %s", buyerDto.buName);
			System.out.println(prompt);
			String strName = scan.nextLine();
		}
	
		
	}

	
	public void update() {
		// TODO Auto-generated method stub
		
	}
	// 세번째 작업함 
	// BuyerDao.selectAll()  실행해서 전체 Buyer List 출력하기
	public void printList() {
		List<BuyerDto> buyerList = buyerDao.selectAll();
		System.out.println(Line.dLine(70));
		System.out.println("우리상사 ERP -고객리스트");
		System.out.println(Line.sLine(70));
		System.out.println("고객ID\t고객이름\t 전화번호\t 주소");
		System.out.println(Line.sLine(70));
		this.printList(buyerList);
		System.out.println(Line.dLine(70));
	}
	//두번째 작업함
	public void printList(List<BuyerDto> buyerList) {
		for(BuyerDto dto :buyerList) {
			//printBuyer 출력 호출
			this.printBuyer(dto);
		}
	}
	// 첫번째 작업함
	public void printBuyer(BuyerDto buyerDto) {
		// 한 학생의 정보를 출력하기
		System.out.printf("%s\t",buyerDto.buId);
		System.out.printf("%s\t",buyerDto.buName);
		System.out.printf("%s\t",buyerDto.buTel);
		System.out.printf("%s\n",buyerDto.buAddr);	
	}
	@Override
	public BuyerDto findById() {
		while(true) {
			System.out.println(Line.dLine(70));
			System.out.println("고객정보 조회");
			System.out.println("고객ID를 정수로 입력하세요. (QUIT :종료)");
			System.out.println("고객ID  >>  ");
			String strBuId = scan.nextLine();
			if(strBuId.equals("QUIT")) return null;
			return this.findById(strBuId);
		}
	}

	
	public BuyerDto findById(String strBuId) {
			try {
				int intBuId = Integer.valueOf(strBuId);
				strBuId = String.format("%010d", intBuId);
				BuyerDto buyerDto =buyerDao.findById(strBuId);
				
				if(buyerDto == null) {
					System.out.printf("없는 고객정보 입니다(%s)\n" ,strBuId);
				}
				else {
					System.out.println(AnsiConsol.CYAN(Line.sLine(70)));
					// 고객정보 보여줌
					this.printBuyer(buyerDto);
					System.out.println(AnsiConsol.CYAN(Line.sLine(70)));
					// 고객정보가 null 이 아니면 고객정보 출력
					return buyerDto;
				}
			} catch (Exception e) {
				HelpMessage.ERROR("고객ID는 정수로 입력하세요", String.format("입력한 고객ID :%s", strBuId));
			}

		return null;
	
	}

	
	public BuyerDto findByBuName() {
		while(true) {
			System.out.println(Line.dLine(70));
			System.out.println("고객정보 조회");
			System.out.println("고객 이름을 입력하세요. (QUIT:종료)");
			System.out.println("고객이름  >> ");
			String strBuName = scan.nextLine();
			if(strBuName.equals("QUIT")) break;
			if(strBuName.isBlank()) {
				HelpMessage.ERROR("고객이름은 반드시 입력해야합니다");
				continue;
			}
			//database에서 고객정보 조회
			// return 한 결과는 empty 이거나 한개이상
			List<BuyerDto> buyerList =buyerDao.findByName(strBuName);
			if(buyerList == null || buyerList.isEmpty()) {
				HelpMessage.ERROR("찾는 고객이 없습니다");
				continue;
				// list에 담긴 정보가 두개이상이면 고객아이디 검색 진행
				//  					한개이면 고객정보리스트 리턴
			}else if(buyerList.size() < 2) {
				// 이름으로 검색한 고객의 데이터가 1개뿐인경우 ID를 검색하는 절차 필요 업음
				return buyerList.get(0);
			}
			//list 출력
			System.out.println(AnsiConsol.CYAN(Line.sLine(70)));
			this.printList(buyerList);
			System.out.println(AnsiConsol.CYAN(Line.sLine(70)));
			
			BuyerDto buyerDto = this.findById();
			
			if(buyerDto == null) {
				HelpMessage.ERROR("고객ID를 확인하세요");
				System.out.println("고객 찾기를 그만두시겠습니까?( Y/N )");
				String yesNo =scan.nextLine();
				if(yesNo.equals("Y")) return null;
			}else {
				return buyerDto;
			}
			
		}

		return null;
	}

	
	public BuyerDto findByBuTel() {
		
		
		// TODO Auto-generated method stub
		return null;
	}

	// 자동으로 새로운 고객ID 생성하기
	public String NewBuId() {
		// 가장 큰값을 가져와 있으면 보여줘
		String maxId = buyerDao.getMaxID();
		if(maxId == null || maxId.isBlank()) 
			return String.format("%010d", 1);
		//없으면 가장큰값으로 만들어줘
		int intBuId = Integer.valueOf(maxId);
		maxId = String.format("%010d", intBuId+1);
		return maxId;
	}


	
	
	
	
}
