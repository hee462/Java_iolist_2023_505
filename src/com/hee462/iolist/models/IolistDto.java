package com.hee462.iolist.models;

public class IolistDto {
	public long ioSEQ;//	VARCHAR2
	public String ioDate;//	VARCHAR2
	public String ioTime;//	VARCHAR2
	public String ioBuId;//	VARCHAR2
	public String ioPCode;//	VARCHAR2
	public int ioQuan;//	INT
	public int ioPrice;//	INT
	public int ioTotal;
	
	/*
	 * 판매단가와 수량 private 판매단가 또는 수량을 입력하면
	 * 합계를 자동계산 작업
	 * 
	 * 
	 * */
	
	
	
	public IolistDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IolistDto(long ioSEQ, String ioDate, String ioTime, String ioBuId, String ioPCode, int ioQuan, int ioPrice,
			int ioTotal) {
		super();
		this.ioSEQ = ioSEQ;
		this.ioDate = ioDate;
		this.ioTime = ioTime;
		this.ioBuId = ioBuId;
		this.ioPCode = ioPCode;
		this.ioQuan = ioQuan;
		this.ioPrice = ioPrice;
		this.ioTotal = ioTotal;
	}
		public int getIoQuan() {
		return ioQuan;
	}
	public void setIoQuan(int ioQuan) {
		this.ioQuan = ioQuan;
		this.ioTotal = this.ioQuan * this.ioPrice;
	}
	public int getIoPrice() {
		return ioPrice;
	}
	public void setIoPrice(int ioPrice) {
		this.ioPrice = ioPrice;
		this.ioTotal = this.ioQuan * this.ioPrice;
	}
	@Override
	public String toString() {
		return "IolistDto [ioSEQ=" + ioSEQ + ", ioDate=" + ioDate + ", ioTime=" + ioTime + ", ioBuId=" + ioBuId
				+ ", ioPCode=" + ioPCode + ", ioQuan=" + ioQuan + ", ioPrice=" + ioPrice + ", ioTotal=" + ioTotal + "]";
	}
	

}