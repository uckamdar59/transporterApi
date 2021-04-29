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
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

@Service
public class TransporterServiceImpl implements TransporterService {

	@Autowired
	private TransporterDao transporterdao;

	@Override
	public TransporterCreateResponse addTransporter(Transporter transporter) {
		TransporterCreateResponse createResponse = new TransporterCreateResponse();
		if (transporter.getName() == null) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.nameError);
			return createResponse;
		}
		
		if (transporter.getPhoneNo() == 0) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.phoneNoError);
			return createResponse;
		}
		
		String validate = "[0-9]{10}$";
		Pattern pattern = Pattern.compile(validate);
		Matcher m = pattern.matcher(Long.toString(transporter.getPhoneNo()));
		if(!m.matches()) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.IncorrecPhoneNoError);
			return createResponse;
		}
		
		String a = null;
		a = transporterdao.findByPhoneNo(transporter.getPhoneNo());
		if (a != null) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.accountExist);
			return createResponse;
		}
		
		transporter.setId("transporter:"+UUID.randomUUID());
		transporterdao.save(transporter);
		createResponse.setStatus(CommonConstants.pending);
		createResponse.setMessage(CommonConstants.approveRequest);
		return createResponse;
	}

	@Override
	public List<Transporter> allTransporter() {
		return transporterdao.findAll();
		
	}
	
	@Override
	public Transporter getOneTransporter(String id) {
		try {
			return transporterdao.findById(id).get();
		}
		catch (NoSuchElementException e) {
			return null;
		}
	}
	
	@Override
	public List<Transporter> getApproved(Boolean approved) {
		return transporterdao.findByApproved(approved);
	}

	@Override
	public TransporterUpdateResponse updateTransporter(String id, Transporter updateTransporter) {
		TransporterUpdateResponse updateResponse = new TransporterUpdateResponse();
		Transporter transporter = new Transporter();
		Optional<Transporter> T = transporterdao.findById(id);
		if(T.isPresent()) {
			transporter = T.get();
		}
		else {
			updateResponse.setStatus(CommonConstants.notFound);
			updateResponse.setMessage(CommonConstants.accountNotExist);
			return updateResponse;
		}

		if (updateTransporter.getPhoneNo() != 0) {			
			updateResponse.setStatus(CommonConstants.error);
			updateResponse.setMessage(CommonConstants.phoneNoUpdateError);
			return updateResponse;
		}

		if (updateTransporter.getName() != null) {
			transporter.setName(updateTransporter.getName());
		}

		if (updateTransporter.getKyc() != null) {
			transporter.setKyc(updateTransporter.getKyc());
		}
		
		if (updateTransporter.getCompanyName() != null) {
			transporter.setCompanyName(updateTransporter.getCompanyName());
		}

		transporter.setApproved(updateTransporter.isApproved());
		transporterdao.save(transporter);
		updateResponse.setStatus(CommonConstants.success);
		updateResponse.setMessage(CommonConstants.updateSuccess);
		return updateResponse;
	}

	@Override
	public TransporterDeleteResponse deleteTransporter(String id) {
		TransporterDeleteResponse deleteResponse = new TransporterDeleteResponse();
		Transporter transporter = new Transporter();
		Optional<Transporter> T = transporterdao.findById(id);
		 
		if( T.isPresent()) {
			transporter = T.get();
			transporterdao.delete(transporter);
			deleteResponse.setStatus(CommonConstants.success);
			deleteResponse.setMessage(CommonConstants.deleteSuccess);
			return deleteResponse;
		}
		else {
			deleteResponse.setStatus(CommonConstants.notFound);
			deleteResponse.setMessage(CommonConstants.accountNotExist);
			return deleteResponse;
		}
		 
	}

}
