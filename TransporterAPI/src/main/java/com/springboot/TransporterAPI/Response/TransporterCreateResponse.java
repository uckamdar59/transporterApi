package com.springboot.TransporterAPI.Response;

import lombok.Data;

@Data
public class TransporterCreateResponse {
	private String transporterId;
	private String status;
	private String message;
}
