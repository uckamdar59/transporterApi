package com.springboot.TransporterAPI.Model;

import lombok.Data;

@Data
public class LoadTransporter {
	private String name;
	private Long phoneNo = null;
	private String kyc;
	private String companyName;
	private boolean approved;
}
