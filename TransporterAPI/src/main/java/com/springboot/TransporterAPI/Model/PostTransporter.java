package com.springboot.TransporterAPI.Model;

import lombok.Data;

@Data
public class PostTransporter {
	
	private Long phoneNo;
	private String transporterLocation;
	private String transporterName;
	private String companyName;
	private String kyc;
	
}
