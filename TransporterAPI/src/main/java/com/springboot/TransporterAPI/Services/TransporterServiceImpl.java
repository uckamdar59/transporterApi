package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.TransporterCreateRequest;
import com.springboot.TransporterAPI.Model.TransporterDeleteRequest;
import com.springboot.TransporterAPI.Model.TransporterUpdateRequest;

@Service
public class TransporterServiceImpl implements TransporterService {

	@Autowired
	private TransporterDao transporterdao;
	private CommonConstants constants = new CommonConstants() ;

	@Override
	public TransporterCreateRequest addTransporter(Transporter transporter) {
		// TODO Auto-generated method stub
		TransporterCreateRequest createRequest = new TransporterCreateRequest();
		if (transporter.getName() == null) {
			createRequest.setStatus(constants.getError());
			createRequest.setMessage(constants.getNameError());
			return createRequest;
		}
		
		if (transporter.getPhoneNo() == 0) {
			createRequest.setStatus(constants.getError());
			createRequest.setMessage(constants.getPhoneNoError());
			return createRequest;
		}
		
		if(String.valueOf(transporter.getPhoneNo()).length() != 10) {
			createRequest.setStatus(constants.getError());
			createRequest.setMessage("Enter 10 digits phone number");
			return createRequest;
		}
		
		String a = null;
		a = transporterdao.findByPhoneNo(transporter.getPhoneNo());
		if (a != null) {
			createRequest.setStatus(constants.getError());
			createRequest.setMessage(constants.getAccountExist());
			return createRequest;
		}
		
		transporter.setId("transporter:"+UUID.randomUUID());
		transporterdao.save(transporter);
		createRequest.setStatus(constants.getPending());
		createRequest.setMessage(constants.getApproveRequest());
		return createRequest;
	}

	
	@Override
	public List<Transporter> allTransporter() {
		// TODO Auto-generated method stub
		List<Transporter> a =  transporterdao.findAll();
		System.out.println("Data is: " +a);
		return a;
	}
	
	
	@Override
	public List<Transporter> getApproved(Boolean approved) {
		// TODO Auto-generated method stub
		List<Transporter> b =  transporterdao.findByApproved(approved);
		System.out.println(b);
		return b;
	}

	@Override
	public TransporterUpdateRequest updateTransporter(String id, Transporter updateTransporter) {
		// TODO Auto-generated method stub
		TransporterUpdateRequest updateRequest = new TransporterUpdateRequest();
		Transporter transporter = new Transporter();
		Optional<Transporter> T = transporterdao.findById(id);
		if(T.isPresent()) {
			transporter = T.get();
		}
		else {
			updateRequest.setStatus(constants.getNotFound());
			updateRequest.setMessage(constants.getAccountNotExist());
			return updateRequest;
		}

		if (updateTransporter.getPhoneNo() != 0) {			
			updateRequest.setStatus(constants.getError());
			updateRequest.setMessage(constants.getPhoneNoUpdateError());
			return updateRequest;
		}

		if (updateTransporter.getName() != null) {
			transporter.setName(updateTransporter.getName());
		}

		if (updateTransporter.getKyc() != null) {
			transporter.setKyc(updateTransporter.getKyc());
		}

		transporter.setApproved(updateTransporter.isApproved());
		transporterdao.save(transporter);
		updateRequest.setStatus(constants.getSuccess());
		updateRequest.setMessage(constants.getUpdateSuccess());
		return updateRequest;
	}

	@Override
	public TransporterDeleteRequest deleteTransporter(String id) {
		// TODO Auto-generated method stub
		TransporterDeleteRequest deleteRequest = new TransporterDeleteRequest();
		Transporter transporter = new Transporter();
		Optional<Transporter> T = transporterdao.findById(id);
		 
		if( T.isPresent()) {
			transporter = T.get();
			transporterdao.delete(transporter);
			deleteRequest.setStatus(constants.getSuccess());
			deleteRequest.setMessage(constants.getDeleteSuccess());
			return deleteRequest;
		}
		else {
			deleteRequest.setStatus(constants.getNotFound());
			deleteRequest.setMessage(constants.getAccountNotExist());
			return deleteRequest;
		}
		 
	}

	@Override
	public Transporter getOneTransporter(String id) {
		// TODO Auto-generated method stub
		return transporterdao.getById(id);
	}

}
