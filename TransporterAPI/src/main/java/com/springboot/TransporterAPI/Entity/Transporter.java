package com.springboot.TransporterAPI.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Transporter")
@Entity
public class Transporter {
	@Id
	private String id;
	private Long phoneNo = null;
	private String transporterLocation;
	private String name;
	private String companyName;
	private String kyc;
	private boolean companyApproved;
	private boolean transporterApproved;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean accountVerificationInProgress;
}
