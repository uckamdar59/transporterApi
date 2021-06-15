package com.springboot.TransporterAPI.Services;

import java.util.List;
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
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

@Service
public class TransporterServiceImpl implements TransporterService {

	@Autowired
	private TransporterDao transporterdao;

	@Override
	public TransporterCreateResponse addTransporter(PostTransporter postTransporter) {
		String companyName = null, name = null, location = null;
		TransporterCreateResponse createResponse = new TransporterCreateResponse();
		Transporter transporter = new Transporter();
		
		if (postTransporter.getPhoneNo() == null) {
			createResponse.setTransporterId(CommonConstants.idNotGenerated);
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.phoneNoError);
			return createResponse;
		}
		
		String validate = "[0-9]{10}$";
		Pattern pattern = Pattern.compile(validate);
		Matcher m = pattern.matcher(Long.toString(postTransporter.getPhoneNo()));
		if(!m.matches()) {
			createResponse.setTransporterId(CommonConstants.idNotGenerated);
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.IncorrecPhoneNoError);
			return createResponse;
		}
		
		Optional<Transporter> t = transporterdao.findByPhoneNo(postTransporter.getPhoneNo());

		if (t.isPresent()) {
			createResponse.setTransporterId(t.get().getId());
			createResponse.setPhoneNo(t.get().getPhoneNo());
			createResponse.setName(t.get().getName());
			createResponse.setTransporterLocation(t.get().getTransporterLocation());
			createResponse.setCompanyName(t.get().getCompanyName());
			createResponse.setKyc(t.get().getKyc());
			createResponse.setTransporterApproved(t.get().isTransporterApproved());
			createResponse.setCompanyApproved(t.get().isCompanyApproved());
			createResponse.setAccountVerificationInProgress(t.get().isAccountVerificationInProgress());
			createResponse.setStatus(CommonConstants.error);
			createResponse.setMessage(CommonConstants.accountExist);
			return createResponse;
		}
		
		if(postTransporter.getTransporterLocation() != null) {
			if (postTransporter.getTransporterLocation().trim().length()<1) {
				createResponse.setTransporterId(CommonConstants.idNotGenerated);
				createResponse.setStatus(CommonConstants.error);
				createResponse.setMessage(CommonConstants.emptyLocationError);
				return createResponse;
			}
			location = postTransporter.getTransporterLocation().trim();
		}
		
		if (postTransporter.getName() != null) {
			if (postTransporter.getName().trim().length()<1) {
				createResponse.setTransporterId(CommonConstants.idNotGenerated);
				createResponse.setStatus(CommonConstants.error);
				createResponse.setMessage(CommonConstants.emptyNameError);
				return createResponse;
			}
			name = postTransporter.getName().trim();
		}
		
		if (postTransporter.getCompanyName() != null) {
			if (postTransporter.getCompanyName().trim().length()<1) {
				createResponse.setTransporterId(CommonConstants.idNotGenerated);
				createResponse.setStatus(CommonConstants.error);
				createResponse.setMessage(CommonConstants.emptyCompanyNameError);
				return createResponse;
			}
			companyName = postTransporter.getCompanyName().trim();
		}
		
		transporter.setId("transporter:"+UUID.randomUUID());
		transporter.setPhoneNo(postTransporter.getPhoneNo());
		transporter.setTransporterLocation(location);
		transporter.setName(name);
		transporter.setCompanyName(companyName);
		transporter.setKyc(postTransporter.getKyc());
		transporter.setTransporterApproved(false);
		transporter.setCompanyApproved(false);
		transporter.setAccountVerificationInProgress(false);
		transporterdao.save(transporter);
		
		createResponse.setTransporterId(transporter.getId());
		createResponse.setPhoneNo(transporter.getPhoneNo());
		createResponse.setName(transporter.getName());
		createResponse.setTransporterLocation(transporter.getTransporterLocation());
		createResponse.setCompanyName(transporter.getCompanyName());
		createResponse.setKyc(transporter.getKyc());
		createResponse.setTransporterApproved(transporter.isTransporterApproved());
		createResponse.setCompanyApproved(transporter.isCompanyApproved());
		createResponse.setAccountVerificationInProgress(transporter.isAccountVerificationInProgress());
		createResponse.setStatus(CommonConstants.pending);
		createResponse.setMessage(CommonConstants.approveRequest);
		return createResponse;
		
	}
	
	@Override
	public Transporter getOneTransporter(String id) {
		Optional<Transporter> S = transporterdao.findById(id);
		if(S.isPresent()) {
			return transporterdao.findById(id).get();
		}
		return null;
	}
	
	@Override
	public List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, Integer pageNo) {
		if(pageNo == null) {
			pageNo = 0;
		}
		
		Pageable page = PageRequest.of(pageNo, 2);
		if((companyApproved != null) && (transporterApproved != null)) {
			return transporterdao.findByTransporterCompanyApproved(transporterApproved, companyApproved, page);
		}
		
		if(transporterApproved != null) {
			return transporterdao.findByTransporterApproved(transporterApproved, page);
		}
		if(companyApproved != null) {
			return transporterdao.findByCompanyApproved(companyApproved, page);
		}
		
		return transporterdao.findAll(page).getContent();
	}

	@Override
	public TransporterUpdateResponse updateTransporter(String id, UpdateTransporter updateTransporter) {
		TransporterUpdateResponse updateResponse = new TransporterUpdateResponse();
		Transporter transporter = new Transporter();
		Optional<Transporter> T = transporterdao.findById(id);
		if(T.isPresent()) {
			transporter = T.get();
			if (updateTransporter.getPhoneNo() != null) {			
				updateResponse.setStatus(CommonConstants.error);
				updateResponse.setMessage(CommonConstants.phoneNoUpdateError);
				return updateResponse;
			}
	
			if (updateTransporter.getName() != null) {
				if (updateTransporter.getName().trim().length()<1) {
					updateResponse.setStatus(CommonConstants.error);
					updateResponse.setMessage(CommonConstants.emptyCompanyNameError);
					return updateResponse;
				}
				transporter.setName(updateTransporter.getName().trim());
			}
			
			if (updateTransporter.getTransporterLocation() != null) {
				if (updateTransporter.getTransporterLocation().trim().length()<1) {
					updateResponse.setStatus(CommonConstants.error);
					updateResponse.setMessage(CommonConstants.emptyCompanyNameError);
					return updateResponse;
				}
				transporter.setTransporterLocation(updateTransporter.getTransporterLocation().trim());
			}
			
			if (updateTransporter.getCompanyName() != null) {
				if (updateTransporter.getCompanyName().trim().length()<1) {
					updateResponse.setStatus(CommonConstants.error);
					updateResponse.setMessage(CommonConstants.emptyCompanyNameError);
					return updateResponse;
				}
				transporter.setCompanyName(updateTransporter.getCompanyName().trim());
			}
			
			if (updateTransporter.getKyc() != null) {
				transporter.setKyc(updateTransporter.getKyc());
			}
			
			if (updateTransporter.getTransporterApproved() != null) {
				transporter.setTransporterApproved(updateTransporter.getTransporterApproved());
			}
			
			if (updateTransporter.getCompanyApproved() != null) {
				transporter.setCompanyApproved(updateTransporter.getCompanyApproved());
			}
			
			if(updateTransporter.getAccountVerificationInProgress() != null) {
				transporter.setAccountVerificationInProgress(updateTransporter.getAccountVerificationInProgress());
			}
	
			transporterdao.save(transporter);
			updateResponse.setTransporterId(transporter.getId());
			updateResponse.setPhoneNo(transporter.getPhoneNo());
			updateResponse.setName(transporter.getName());
			updateResponse.setTransporterLocation(transporter.getTransporterLocation());
			updateResponse.setCompanyName(transporter.getCompanyName());
			updateResponse.setKyc(transporter.getKyc());
			updateResponse.setTransporterApproved(transporter.isTransporterApproved());
			updateResponse.setCompanyApproved(transporter.isCompanyApproved());
			updateResponse.setAccountVerificationInProgress(transporter.isAccountVerificationInProgress());
			updateResponse.setStatus(CommonConstants.success);
			updateResponse.setMessage(CommonConstants.updateSuccess);
			return updateResponse;
		
		}
		else {
			updateResponse.setStatus(CommonConstants.notFound);
			updateResponse.setMessage(CommonConstants.accountNotExist);
			return updateResponse;
		}
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
