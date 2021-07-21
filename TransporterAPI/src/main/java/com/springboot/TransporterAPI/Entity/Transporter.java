package com.springboot.TransporterAPI.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;


@Data
@Table(name = "Transporter")
@Entity
public class Transporter {
	
	@Id
	private String transporterId;
	@Column(unique=true)
	@NotBlank(message = "Phone no. cannot be blank!")
	//	"^[6-9]\\d{9}$", "(^$|[0-9]{10})"
	@Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number") 
	private String phoneNo;
	private String transporterName;
	private String companyName;
	private String transporterLocation;
	private String kyc;
	private boolean companyApproved;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean transporterApproved;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean accountVerificationInProgress;
	
}
