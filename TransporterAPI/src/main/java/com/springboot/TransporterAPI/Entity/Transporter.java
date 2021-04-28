package com.springboot.TransporterAPI.Entity;

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
	
	private String name;
	private long phoneNo;
	private String kyc;
	private String companyName;
	private boolean approved;
}
