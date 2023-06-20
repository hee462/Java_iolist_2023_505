package com.hee462.iolist.service.impl;

import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.hee462.iolist.config.DBConnection;
import com.hee462.iolist.config.HelpMessage;
import com.hee462.iolist.config.Line;
import com.hee462.iolist.models.BuyerDto;
import com.hee462.iolist.persistance.BuyerDao;

public class BuyerServiceImplV2 extends BuyerServiceImplV1{
	//BuyerDao, Sacnner 객체를 V1상속받아 선언한 상태
	// V2 에서 객체를 초기화 하는 생성자를 만들어야 한다
	// 클래스를 상속받을때
	// public 또는 protected 로 선언한 변수와 메서드는 상속을 받는다
	//하지만 V1 클래스의 생성자는 상속을 받지 않는다
	// V2 에서 생성자를 만들어 객체들을 초기화 하는 코드를 사용한다
	public BuyerServiceImplV2() {
		SqlSession session = DBConnection.getSessionFactory().openSession(true);
		buyerDao = session.getMapper(BuyerDao.class);
		scan = new Scanner(System.in);
	}
	
	// V1에서 만들어진 method를 재정의 한다
	@Override
	public BuyerDto findById() {
		while(true) {
			System.out.println("고객ID를 정수로 입력하세요 (QUIT : 종료)");
			System.out.print("고객  ID  >>  ");
			String strBuId = scan.nextLine();
			if(strBuId.isBlank()) {
				HelpMessage.ERROR("고객을 찾으려면 ID를 입력해 주세요");
				continue;
			}
			try {
				int intBuId = Integer.valueOf(strBuId);
				// 고객 ID 형으로 변환
				strBuId = String.format("%010d", intBuId);
			} catch (Exception e) {
				HelpMessage.ERROR(String.format("고객ID는 정수로 입력해주세요(%s)", strBuId));
				continue;
			}
			BuyerDto buyerDto = buyerDao.findById(strBuId);
			if(buyerDto == null) {
				HelpMessage.ERROR(String.format("입력한 ID는 없습니다(%s)", strBuId));
		}
			return buyerDto;
		}
		
	}// findById end
	
	protected String inputBuId() {
		while(true) {
			System.out.println(Line.dLine(70));
			System.out.println("고객정보를 확인합니다");
			System.out.println("고객 ID를 입력해 주세요.(QUIT : 종료)");
			System.out.println("고객ID  >>  ");
			String strBuId = scan.nextLine();
			if(strBuId.equals("QUIT")) return strBuId;
			
			if(strBuId.isBlank()) {
				HelpMessage.CONFIRM("고객ID가 없습니다", "고객ID를 새로 생성할까요? (Y/N)");
				String yesNo =scan.nextLine();
				if(yesNo.equalsIgnoreCase("Y")) return this.NewBuId();
				else {
					HelpMessage.ERROR("고객 ID 생성 취소"); 
					continue;
				}
			}
			try {
				int intBuId = Integer.valueOf(strBuId);
				strBuId = String.format("%010d", intBuId);
				return strBuId;
			} catch (Exception e) {
				HelpMessage.ERROR(String.format("고객ID는 정수로만 입력하세요(%s)", strBuId));
				continue;
			
			}
			
		}
	}
	@Override
	public void insert() {
		//buyeDto 선언만
		BuyerDto buyerDto=null;
		
		while(true) {
			String strBuId = this.inputBuId();
			if(strBuId.equals("QUIT")) return;
			// 입력한 고객 ID로 고객정보 조회
			buyerDto = buyerDao.findById(strBuId);
			// 새로운 고객 들록하기
			if(buyerDto == null) {
				buyerDto = new BuyerDto();
				buyerDto.buId = strBuId;
				// 고객정보가 기존 고객임
			}else if(buyerDto != null) {
				String message = String.format("등록된 고객ID 입니다\n\t" +"고객명 : (%s)\n\t" +"전화번호 :(%s)",
						buyerDto.buName ,buyerDto.buTel);
				String prompt = "고객정보를 수정할까요? (Y/N)";
				HelpMessage.CONFIRM(message, prompt);
						String yesNo = scan.nextLine();
				if(!yesNo.equalsIgnoreCase("Y")) continue;
			}
			break;
		}//while end
		HelpMessage.ALERT("고객ID 확인 : " + buyerDto.buId);
		// 고객이름 입력하기
		while(true) {
			String prompt = buyerDto == null|| buyerDto.buName == null?
					"새로운 고객이름"
					: String.format("고객명 수정 (%s) :유지하려면 Enter", buyerDto.buName);
			System.out.print(prompt);
			String strName = scan.nextLine();
			if(strName.equals("QUIT")) return;
			if(buyerDto.buName == null && strName.isEmpty()) {
				HelpMessage.ERROR("고객이름은 반드시 입력해야 합니다");
				continue;
			}else if(!strName.isEmpty()) {
				buyerDto.buName = strName;
			}
			break;
		}// while end
		HelpMessage.ALERT("입력한 고객이름 확인 : " +buyerDto.buName);
		
		
		
		// 전화번호 입력하기
				while(true) {
					String prompt = buyerDto == null|| buyerDto.buTel == null?
							"새로운 전화번호"
							: String.format("고객 전화번호 수정 (%s) :유지하려면 Enter", buyerDto.buTel);
					System.out.print(prompt);
					String strTel = scan.nextLine();
					if(strTel.equals("QUIT")) return;
					if(buyerDto.buTel == null && strTel.isEmpty()) {
						HelpMessage.ERROR("전화번호는 반드시 입력해야 합니다");
						continue;
					}else if(!strTel.isEmpty()) {
						buyerDto.buTel = strTel;
					}
					break;
				}// while end
				HelpMessage.ALERT("입력한 전화번호 확인 : " +buyerDto.buTel);
				
				// 주소 입력하기
				while(true) {
					String prompt = buyerDto == null|| buyerDto.buAddr == null?
							"새로운 주소"
							: String.format("고객 주소 수정 (%s) :유지하려면 Enter", buyerDto.buAddr);
					System.out.print(prompt);
					String strAddr = scan.nextLine();
					if(strAddr.equals("QUIT")) return;
					if(buyerDto.buAddr == null && strAddr.isEmpty()) {
						HelpMessage.ERROR("주소는 반드시 입력해야 합니다");
						continue;
					}else if(!strAddr.isEmpty()) {
						buyerDto.buAddr = strAddr;
					}
					break;
				}// while end
				HelpMessage.ALERT("입력한 주소 확인 : " +buyerDto.buAddr);
				
				String okMessage = String.format("고객ID : %s\n"
						                        +"고객이름 : %s\n" 
						                        +"전화번호 : %s\n" 
						                        +"주소 : %s\n" 
						                        , buyerDto.buId
						                        , buyerDto.buName
						                        , buyerDto.buTel
						                        , buyerDto.buAddr);
				HelpMessage.ALERT(okMessage);
				
				int result =0;
				// 중복된 값이 있으면 추가 없으면 업데이트 진행하는 코드
				try {
					result = buyerDao.insert(buyerDto);		
					if(result >0 )HelpMessage.OK("데이터 추가 완료");
				} catch (Exception e) {
					result = buyerDao.update(buyerDto);
					if(result >0 )HelpMessage.OK("데이터 수정 완료");
				}
				if(result <1) {
					HelpMessage.ERROR("데이터 추가, 수정 실패");
				}
				
				
	}
	
	
	
	
	
	
	
}
