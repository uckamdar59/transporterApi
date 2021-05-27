package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.LoadTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

@Service
public class TransporterServiceImpl implements TransporterService {

	@Autowired
	private TransporterDao transporterdao;

	@Override
	public TransporterCreateResponse addTransporter(LoadTransporter loadTransporter) {
		TransporterCreateResponse createResponse = new TransporterCreateResponse();
		Transporter transporter = new Transporter();
		if (loadTransporter.getName() == null) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.nameError);
			return createResponse;
		}
		
		if (loadTransporter.getName().trim().length()<1) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.emptyNameError);
			return createResponse;
		}
		
		if (loadTransporter.getCompanyName().trim().length()<1) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.emptyCompanyNameError);
			return createResponse;
		}
		
		if (loadTransporter.getPhoneNo() == null) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.phoneNoError);
			return createResponse;
		}
		
		String validate = "[0-9]{10}$";
		Pattern pattern = Pattern.compile(validate);
		Matcher m = pattern.matcher(Long.toString(loadTransporter.getPhoneNo()));
		if(!m.matches()) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.IncorrecPhoneNoError);
			return createResponse;
		}
		
		String a = null;
		a = transporterdao.findByPhoneNo(loadTransporter.getPhoneNo());
		if (a != null) {
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.accountExist);
			return createResponse;
		}
		
		transporter.setId("transporter:"+UUID.randomUUID());
		transporter.setName(loadTransporter.getName().trim());
		transporter.setCompanyName(loadTransporter.getCompanyName().trim());
		transporter.setPhoneNo(loadTransporter.getPhoneNo());
		transporter.setKyc(loadTransporter.getKyc());
		transporter.setApproved(loadTransporter.isApproved());
		transporterdao.save(transporter);
		createResponse.setStatus(CommonConstants.pending);
		createResponse.setMessage(CommonConstants.approveRequest);
		return createResponse;
		
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
	public List<Transporter> getTransporters(Boolean approved, Integer pageNo) {
		if(pageNo == null) {
			pageNo = 0;
		}
		
		Pageable page = PageRequest.of(pageNo, 2);
		if(approved!=null) {
			return transporterdao.findByApproved(approved, page);
		}
		
		return transporterdao.findAll(page).getContent();
	}

	@Override
	public TransporterUpdateResponse updateTransporter(String id, LoadTransporter updateTransporter) {
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

		if (updateTransporter.getPhoneNo() != null) {			
			updateResponse.setStatus(CommonConstants.error);
			updateResponse.setMessage(CommonConstants.phoneNoUpdateError);
			return updateResponse;
		}
		
		if (updateTransporter.getName().trim().length()<1) {
			updateResponse.setStatus(CommonConstants.error);
			updateResponse.setMessage(CommonConstants.emptyNameError);
			return updateResponse;
		}
		
		if (updateTransporter.getCompanyName().trim().length()<1) {
			updateResponse.setStatus(CommonConstants.error);
			updateResponse.setMessage(CommonConstants.emptyCompanyNameError);
			return updateResponse;
		}

		if (updateTransporter.getName() != null) {
			transporter.setName(updateTransporter.getName().trim());
		}

		if (updateTransporter.getKyc() != null) {
			transporter.setKyc(updateTransporter.getKyc());
		}
		
		if (updateTransporter.getCompanyName() != null) {
			transporter.setCompanyName(updateTransporter.getCompanyName().trim());
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
