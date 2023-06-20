package com.hee462.iolist.models;

public class IolistDto {
	public long ioSEQ;//	VARCHAR2
	public String ioDate;//	VARCHAR2
	public String ioTime;//	VARCHAR2
	public String ioBuId;//	VARCHAR2
	public int ioPCode;//	VARCHAR2
	public int ioQuan;//	INT
	public int ioPrice;//	INT
	public String ioTotal;
	public IolistDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IolistDto(long ioSEQ, String ioDate, String ioTime, String ioBuId, int ioPCode, int ioQuan, int ioPrice,
			String ioTotal) {
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
	@Override
	public String toString() {
		return "IolistDto [ioSEQ=" + ioSEQ + ", ioDate=" + ioDate + ", ioTime=" + ioTime + ", ioBuId=" + ioBuId
				+ ", ioPCode=" + ioPCode + ", ioQuan=" + ioQuan + ", ioPrice=" + ioPrice + ", ioTotal=" + ioTotal + "]";
	}
	

}