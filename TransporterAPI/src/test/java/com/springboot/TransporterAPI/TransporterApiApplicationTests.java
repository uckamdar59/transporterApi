package com.springboot.TransporterAPI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.TransporterCreateRequest;
import com.springboot.TransporterAPI.Model.TransporterDeleteRequest;
import com.springboot.TransporterAPI.Model.TransporterUpdateRequest;
import com.springboot.TransporterAPI.Services.TransporterService;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransporterApiApplicationTests {

	@Autowired
	TransporterService service;

	@MockBean
	TransporterDao transporterDao;

	//Unit Testing for saving
	@Test
	public void saveTransporterTest() {
		Transporter transporter = new Transporter();
		transporter.setId("transporter:01");
		transporter.setName("Ram");
		transporter.setKyc("kyc");
		transporter.setApproved(false);
		transporter.setPhoneNo(1234456789);
		TransporterCreateRequest tcr = new TransporterCreateRequest();
		tcr.setStatus("Pending");
		tcr.setMessage("Please wait for liveasy will approve your request");
		when(transporterDao.save(transporter)).thenReturn(transporter);
		assertEquals(tcr, service.addTransporter(transporter));
	}
	
	//Unit Testing for saving without name
	@Test
	public void saveWithoutNameTest() {
		Transporter transporter = new Transporter();
		transporter.setId("transporter:01");
		transporter.setKyc("kyc");
		transporter.setApproved(false);
		transporter.setPhoneNo(1234456789);
		TransporterCreateRequest tcr = new TransporterCreateRequest();
		tcr.setStatus("Error");
		tcr.setMessage("Enter name");
		when(transporterDao.save(transporter)).thenReturn(transporter);
		assertEquals(tcr, service.addTransporter(transporter));
	}
	
	//Unit Testing for saving without phone number
	@Test
	public void saveWithoutPhoneNoTest() {
		Transporter transporter = new Transporter();
		transporter.setId("transporter:01");
		transporter.setName("Ram");
		transporter.setKyc("kyc");
		transporter.setApproved(false);
		TransporterCreateRequest tcr = new TransporterCreateRequest();
		tcr.setStatus("Error");
		tcr.setMessage("Enter phone number");
		when(transporterDao.save(transporter)).thenReturn(transporter);
		assertEquals(tcr, service.addTransporter(transporter));
	}
	
	//Unit Testing for saving with incorrect phone number
	@Test
	public void saveWithIncorrectPhoneNoTest() {
		Transporter transporter = new Transporter();
		transporter.setId("transporter:01");
		transporter.setName("Ram");
		transporter.setKyc("kyc");
		transporter.setApproved(false);
		transporter.setPhoneNo(12344);
		TransporterCreateRequest tcr = new TransporterCreateRequest();
		tcr.setStatus("Error");
		tcr.setMessage("Enter 10 digits phone number");
		when(transporterDao.save(transporter)).thenReturn(transporter);
		assertEquals(tcr, service.addTransporter(transporter));
	}
	
	
	//Unit Testing for saving for getting all transporters
	@Test
	public void getTransporterTest() {
		 Transporter transporter = new Transporter(); 
		 transporter.setId("transporter:01");
		 transporter.setName("Ram");
		 transporter.setKyc("kyc");
		 transporter.setApproved(false);
		 transporter.setPhoneNo(1234456789);
		 Transporter transporter2 = new Transporter();
		 transporter2.setId("transporter:02");
		 transporter2.setName("Seeta");
		 transporter2.setKyc("kycSeeta");
		 transporter2.setApproved(true);
		 transporter2.setPhoneNo(987654321);
		 when(transporterDao.findAll()).thenReturn(Stream.of(transporter, transporter2).collect(Collectors.toList()));
		 assertEquals(2, service.allTransporter().size()); 
	}
	
	//Unit Testing for saving for getting all transporters by approved value
	@Test
	public void getApprovedTest() {
		 Transporter transporter = new Transporter(); 
		 transporter.setId("transporter:01");
		 transporter.setName("Ram");
		 transporter.setKyc("kyc");
		 transporter.setApproved(false);
		 transporter.setPhoneNo(1234456789);
		 Transporter transporter2 = new Transporter();
		 transporter2.setId("transporter:02");
		 transporter2.setName("Seeta");
		 transporter2.setKyc("kycSeeta");
		 transporter2.setApproved(true);
		 transporter2.setPhoneNo(987654321);
		 Transporter transporter3 = new Transporter();
		 transporter3.setId("transporter:02");
		 transporter3.setName("Lakshman");
		 transporter3.setKyc("kycLakshman");
		 transporter3.setApproved(true);
		 transporter3.setPhoneNo(987654324);
		 when(transporterDao.findByApproved(true)).thenReturn(Stream.of(transporter2, transporter3).collect(Collectors.toList()));
		 assertEquals(2, service.getApproved(true).size()); 
	}
	
	 
	//Unit Testing for deleting transporter
	@Test 
	public void deleteTransporterTest() { 
		 Transporter transporter = new Transporter();
		 transporter.setId("transporter:01");
		 transporter.setName("Ram");
		 transporter.setKyc("kyc");
		 transporter.setApproved(false);
		 transporter.setPhoneNo(1234456789);
		 transporterDao.delete(transporter);
		 verify(transporterDao,times(1)).delete(transporter);
		 
	}
	 

}
