package com.hee462.iolist.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.hee462.iolist.config.DBConnection;
import com.hee462.iolist.config.HelpMessage;
import com.hee462.iolist.config.Line;
import com.hee462.iolist.models.BuyerDto;
import com.hee462.iolist.models.IolistDto;
import com.hee462.iolist.models.ProductDto;
import com.hee462.iolist.persistance.BuyerDao;
import com.hee462.iolist.persistance.IolistDao;
import com.hee462.iolist.persistance.ProductDao;
import com.hee462.iolist.service.BuyerService;
import com.hee462.iolist.service.IolistService;
import com.hee462.iolist.service.ProductService;

public class IolistServiceImplV1 implements IolistService {
	// Iolist 와 Buyer,Product 기능을 서로 연결하는 것
	// 의존성 주입(Depency Injection)
	protected final Scanner scan;

	protected final IolistDao iolistDao;
	protected final BuyerDao buyerDao;
	protected final ProductDao productDao;

	protected final BuyerService buyerService;
	protected final ProductService productService;

	public IolistServiceImplV1() {

		scan = new Scanner(System.in);

		SqlSession sqlSession = DBConnection.getSessionFactory().openSession(true);

		iolistDao = sqlSession.getMapper(IolistDao.class);
		buyerDao = sqlSession.getMapper(BuyerDao.class);
		productDao = sqlSession.getMapper(ProductDao.class);

		buyerService = new BuyerServiceImplV2();
		productService = new ProductServiceImplV1();

	}

	@Override
	public void printIolist(IolistDto iolist) {
		System.out.printf("%d\t", iolist.ioSEQ);
		System.out.printf("%s\t", iolist.ioDate);
		System.out.printf("%s\t", iolist.ioTime);

		System.out.printf("%s\t", iolist.ioBuId);
		System.out.printf("%s\t", buyerDao.findById(iolist.ioBuId).buName);

		System.out.printf("%s\t", iolist.ioPCode);
		System.out.printf("%s\t", productDao.findById(iolist.ioPCode).pName);

		System.out.printf("%d\t", iolist.getIoPrice());
		System.out.printf("%d\t", iolist.getIoQuan());
		System.out.printf("%d\n", iolist.ioTotal);

	}

	@Override
	public void printList() {
		List<IolistDto> iolists = iolistDao.selectAll();
		this.printList(iolists);

	}

	@Override
	public void printList(List<IolistDto> iolists) {
		System.out.println(Line.sLine(100));
		System.out.println("SEQ\t거래일자\t거리시각\t고객ID\t고객명\t상품코드\t상품명\t판매단가\t수량\t합계");
		System.out.println(Line.sLine(100));
		for (IolistDto dto : iolists) {
			this.printIolist(dto);
		}
	}

	protected Map<String, String> getDateTime() {

		LocalDateTime localDateTime = LocalDateTime.now();

		DateTimeFormatter strDate = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		DateTimeFormatter strTime = DateTimeFormatter.ofPattern("HH-mm-ss");

		String curDate = strDate.format(localDateTime);
		String curTime = strTime.format(localDateTime);

		/*
		 * Java의 Map<?,?> class를 사용하여 임시 Dto 객체 만들기 Map class 이용한 객체는 key,value가 쌍으로
		 * 이루어진 데이터 객체 JS의 JSON과 유사한구조 dateTime ={ DATE : curDate, TIME : curTime }
		 * 
		 */

		// String type key, String type의 value를 갖는 임시 Dto객체
		Map<String, String> dateTime = new HashMap<>();
		dateTime.put("DATE", curDate);
		dateTime.put("TIME", curTime);
		return dateTime;
	}

	@Override
	public void input() {
		while (true) {
			// 장바구니 객체 시작
			IolistDto iolistDto = new IolistDto();
			Map<String, String> dateTime = this.getDateTime();

			// 현재 날짜 시각 설정 setting
			iolistDto.ioDate = dateTime.get("DATE");
			iolistDto.ioTime = dateTime.get("TIME");

			System.out.println(Line.dLine(70));
			System.out.println("장바구니 담기 - V1");
			System.out.println(Line.dLine(70));

			BuyerDto buyerDto = null;
			while (true) {
				buyerDto = buyerService.findByBuName();
				if (buyerDto == null) {
					HelpMessage.CONFIRM("장바구니 담기를 종료할까요??", "(Y:종료)");
					System.out.println("상품이름 >> ");
					String yesNo = scan.nextLine();
					if (yesNo.equalsIgnoreCase("Y"))
						return;
					else
						continue;
				}
				break;
			} // 고객정보 조회
			HelpMessage.ALERT("조회한 고객정보 : " + buyerDto.toString());
			iolistDto.ioBuId = buyerDto.buId;

			ProductDto productDto = null;
			while (true) {
				productDto = productService.findByPCode();
				if (productDto == null) {
					HelpMessage.CONFIRM("장바구니 담기를 종료할까요??", "(Y:종료)");
					String yesNo = scan.nextLine();
					if (yesNo.equalsIgnoreCase("Y"))
						return;
					else
						continue;
				}
				break;
			} // 상품정보 end
			HelpMessage.ALERT("조회한 상품정보 : " + productDto.toString());
			
			// 객체담기
			iolistDto.ioPCode = productDto.pCode;
			String message = String.format("조회한 고객 코드 %s, 고객이름 %s", iolistDto.ioBuId, buyerDto.buName);

			message = String.format("검색한 상품코드 : %s ", iolistDto.ioPCode);
			HelpMessage.ALERT(message);

			/*
			 * 판매단가 입력 판매단가는 기본적으로 상품정보에 등록되어있다 보통은 상품정보에 등록된 판매단가를 그대로 사용하고 필요에따라 판매단가
			 * 변경하여사용
			 * 
			 */
			while(true) {
				System.out.println(Line.sLine(70));
				System.out.println("판매단가를 입력해 주세요 QUIT:종료");
				System.out.println("판매단가를 그대로 사용하려면 Enter");
				System.out.printf("판매단가 (%s) >> ",productDto.pOPrice);
				
				String strPrice = scan.nextLine();
				if(strPrice.equals("QUIT")) return;
				
				try {
					int intPrice = Integer.valueOf(strPrice);
					iolistDto.setIoPrice(intPrice);
				} catch (Exception e) {
					// TODO: handle exception
					// 그냥 Enter 를 눌렀을 경우, 
					// 또는 숫자 이외의 문자가 입력된 경우
					if(strPrice.isBlank()) {
						iolistDto.setIoPrice(productDto.pOPrice);
					} else {
						HelpMessage.ERROR(
							String.format("판매단가는 정수로 입력하세요 (%s)", 
									strPrice)
						);
						continue;
					}
				} // end try
				break;
			}
			HelpMessage.ALERT("입력한 판매단가 : " + iolistDto.getIoPrice());
			
			
			while(true) {
				System.out.println("상품 판매 수량을 입력하세요");
				System.out.println("수량 >> ");
				String strQuan = scan.nextLine();
				if(strQuan.equals("QUIT")) return;
				try {
					int intQuan = Integer.valueOf(strQuan);
					iolistDto.setIoQuan(intQuan);
				} catch (Exception e) {
					HelpMessage.ERROR(String.format("수량은 정수로 입력하세요(%s)", strQuan));
					continue;
					// TODO: handle exception
				}
				break;
			}
			HelpMessage.ALERT("입력된 수량 : " + iolistDto.getIoQuan());
			HelpMessage.ALERT("계산된 합계 : "+iolistDto.ioTotal);
			
			int result = iolistDao.insert(iolistDto);
			if(result > 0) {
				HelpMessage.OK("장바구니 추가성공");
			}else {
				HelpMessage.ERROR("장바구니 추가 실패");
			}
			
			
		}// 제일 바깥쪽 while 장바구니 등록연속 진행 위한 코드

	}
	/*
	 * 기간별 거래 리스트
	 * 조회시작 일자, 조회 종료일자를 입력받고
	 * 해당 기간의 리스트를 출력하기
	 */
	@Override
	public void selectBetwenDate() {
		while(true) {
			System.out.println("기간별 리스트를 출력하기 위하여 날짜 입력");
			System.out.println("조회 시작일자를 입력하세요");
			String sDate = this.inputDate();
			if(sDate == null)return;
			
			System.out.println("조회 종료일자를 입력하세요");
			String eDate = this.inputDate();
			if(sDate == null)return;
			
			List<IolistDto> iolists = iolistDao.selectBetwenDate(sDate, eDate);
			this.printList(iolists);
		}

	}

	@Override
	public void selectByBuyer() {
		BuyerDto buyerDto = buyerService.findByBuName();
		if(buyerDto == null) {
			HelpMessage.ERROR("고객 정보가 없습니다");
		}else {
			List<IolistDto> iolists = iolistDao.selectByBuyer(buyerDto.buId);
			this.printList(iolists);
		}

	}

	@Override
	public void selectByProduct() {
		ProductDto productDto = productService.findByPName();
		if(productDto == null) {
			HelpMessage.ERROR("상품 정보를 찾을수 없습니다");
		}else {
			List<IolistDto> iolists = iolistDao.selectByProduct(productDto.pCode);
			this.printList(iolists);
		}
	}
	protected String inputDate() {
		while(true) {
			System.out.println("날짜 입력 >> ");
			String strdate = scan.nextLine();
			if(strdate.equals("QUIT")) return null;
			else if(strdate.isBlank()) {
				HelpMessage.ERROR("날짜를 입력해주세요"); continue;
			}
			SimpleDateFormat checkDate = new SimpleDateFormat("yyyy-MM-dd");
			// 현재 시스템 날짜 getter
			Date date = new Date(System.currentTimeMillis());
			// 날짜형 데이터를 문자열형 데이터 변화  ->"2023-06-21" 형식의 문자열 생성
			String curDate = checkDate.format(date);
			//날짜 형식을 갖춘 문자열형 데이터를 날짜형(Date type)의 데이터로 변환
			// 이 method는 SimpleDateFormatter에 설정된 형식 문자열 (YYYY-MM-dd)에 일치하지 않으면  exception 발생
			try {
				date = checkDate.parse(strdate);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void selectByBuyerBetweenDate() {
		while(true) {
			System.out.println("데이터 조회");
			BuyerDto buyerDto = buyerService.findByBuName();
			if(buyerDto == null) return;
			
			System.out.println("거래 기간을 입력해 주세요");
			System.out.print("조회 시작일자 >> ");
			String sDate = inputDate();
			if(sDate == null) return;

			System.out.print("조회 종료일자 >> ");
			String eDate = inputDate();
			if(eDate == null) return;

			List<IolistDto> iolists 
			= iolistDao.selectByBuyerBetweenDate(
					buyerDto.buId, sDate, eDate);
			
			this.printList(iolists);
		}
		
	}

	@Override
	public void selectByProductBetweenDate() {
		while(true) {
			System.out.println("데이터 조회");
			ProductDto productDto = productService.findByPName();
			if(productDto == null) return;
			
			System.out.println("거래 기간을 입력해 주세요");
			System.out.print("조회 시작일자 >> ");
			String sDate = inputDate();
			if(sDate == null) return;

			System.out.print("조회 종료일자 >> ");
			String eDate = inputDate();
			if(eDate == null) return;

			List<IolistDto> iolists 
			= iolistDao.selectByProductBetweenDate(
					productDto.pCode, sDate, eDate);
			this.printList(iolists);
		}

	}

}
