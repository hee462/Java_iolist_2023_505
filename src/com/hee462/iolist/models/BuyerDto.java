package com.hee462.iolist.models;

public class BuyerDto {
	public String buId;//	VARCHAR
	public String buName;//	nVARCHAR
	public String buTel;//	VARCHAR
	public String buAddr;//	nVARCHAR
	public BuyerDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BuyerDto(String buId, String buName, String buTel, String buAddr) {
		super();
		this.buId = buId;
		this.buName = buName;
		this.buTel = buTel;
		this.buAddr = buAddr;
	}
	@Override
	public String toString() {
		return "BuyerDto [buId=" + buId + ", buName=" + buName + ", buTel=" + buTel + ", buAddr=" + buAddr + "]";
	}

}
