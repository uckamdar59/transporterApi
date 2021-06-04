package com.springboot.TransporterAPI.Response;

import lombok.Data;

@Data
public class TransporterCreateResponse {
	private String transporterId;
	private Long phoneNo = null;
	private String transporterLocation;
	private String name;
	private String companyName;
	private String kyc;
	private Boolean companyApproved;
	private Boolean transporterApproved;
	private String status;
	private String message;
}
