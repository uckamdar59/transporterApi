package com.springboot.TransporterAPI.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.JwtResponse;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransporterServiceImpl implements TransporterService {


	@Autowired
	private TransporterDao transporterdao;

	@Autowired
	private com.springboot.TransporterAPI.SecurityConfig.JwtTokenUtil jwtTokenUtil;


	@Transactional(rollbackFor = Exception.class)
	@Override
	public TransporterCreateResponse addTransporter(PostTransporter postTransporter) {
		log.info("addTransporter service is started");

		String temp="";
		Transporter transporter =new Transporter();
		log.info("221transporter is saved to the database");
		
		TransporterCreateResponse response = new TransporterCreateResponse();
		log.info("331transporter is saved to the database");
		
		Optional<Transporter> t = transporterdao.findByPhoneNo(postTransporter.getPhoneNo());
		log.info("991transporter is saved to the database");
		if (t.isPresent()) {
			response.setTransporterId(t.get().getTransporterId());
			log.info("000transporter is saved to the database");
			response.setPhoneNo(t.get().getPhoneNo());
			log.info("ubh--1transporter is saved to the database");
			response.setTransporterName(t.get().getTransporterName());
			response.setTransporterLocation(t.get().getTransporterLocation());
			response.setCompanyName(t.get().getCompanyName());
			response.setKyc(t.get().getKyc());
			response.setTransporterApproved(t.get().isTransporterApproved());
			response.setCompanyApproved(t.get().isCompanyApproved());
			response.setAccountVerificationInProgress(t.get().isAccountVerificationInProgress());
			response.setMessage(CommonConstants.accountExist);
			return response;
		}

		temp="transporter:"+UUID.randomUUID();
		transporter.setTransporterId(temp);
		response.setTransporterId(temp);
		log.info("441transporter is saved to the database");
		temp=postTransporter.getPhoneNo();
		transporter.setPhoneNo(temp);
		response.setPhoneNo(temp);
		log.info("51transporter is saved to the database");
		temp=postTransporter.getTransporterName();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp.trim());
			response.setTransporterName(temp.trim());
		}

		temp=postTransporter.getCompanyName();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setCompanyName(temp.trim());
			response.setCompanyName(temp.trim());
		}

		temp=postTransporter.getTransporterLocation();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp.trim());
			response.setTransporterLocation(temp.trim());
		}

		temp=postTransporter.getKyc();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setKyc(temp.trim());
			response.setKyc(temp.trim());
		}
		log.info("631transporter is saved to the database");
		transporter.setTransporterApproved(false);
		response.setTransporterApproved(false);

		transporter.setCompanyApproved(false);
		response.setCompanyApproved(false);

		transporter.setAccountVerificationInProgress(false);
		response.setAccountVerificationInProgress(false);

		
		
		log.info("441transporter is saved to the database");
		
		transporterdao.save(transporter);
		log.info("111transporter is saved to the database");
		
		final String token = jwtTokenUtil.generateToken(postTransporter);
		log.info("transporter is saved to the database");

		response.setStatus(CommonConstants.pending);
		response.setMessage(CommonConstants.approveRequest);
response.setJwttoken(token);

		log.info("addTransporter response is returned");
		return response;

	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public Transporter getOneTransporter(String transporterId) {
		log.info("getOneTransporter service is started");
		Optional<Transporter> S = transporterdao.findById(transporterId);
		if(S.isEmpty()) {
			throw new EntityNotFoundException(Transporter.class,"id",transporterId);
		}
		log.info("getOneTransporter response is returned");
		return S.get();
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, Integer pageNo) {
		log.info("getTransporters service is started");
		List<Transporter> list = null;
		if(pageNo == null) {
			pageNo = 0;
		}

		Pageable page = PageRequest.of(pageNo, 15);
		if((companyApproved != null) && (transporterApproved != null)) {
			list = transporterdao.findByTransporterCompanyApproved(transporterApproved, companyApproved, page);
			Collections.reverse(list);
			return list;
		}

		if(transporterApproved != null) {
			list = transporterdao.findByTransporterApproved(transporterApproved, page);
			Collections.reverse(list);
			return list;
		}
		if(companyApproved != null) {
			list = transporterdao.findByCompanyApproved(companyApproved, page);
			Collections.reverse(list);
			return list;
		}

		list = transporterdao.getAll(page);
		Collections.reverse(list);

		log.info("getTransporters response is returned");
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TransporterUpdateResponse updateTransporter(String transporterId, UpdateTransporter updateTransporter) {
		log.info("updateTransporter service is started");

		TransporterUpdateResponse updateResponse = new TransporterUpdateResponse();

		Optional<Transporter> T = transporterdao.findById(transporterId);
		if(T.isEmpty())
			throw new EntityNotFoundException(Transporter.class, "id", transporterId.toString());

		String temp="";
		Transporter transporter = T.get();

		if (updateTransporter.getPhoneNo() != null) {			
			throw new BusinessException("Phone no can't be updated");
		}

		temp=updateTransporter.getTransporterName();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp.trim());
		}

		temp=updateTransporter.getTransporterLocation();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp.trim());
		}

		temp=updateTransporter.getCompanyName();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setCompanyName(temp.trim());
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
		log.info("transporter is updated in the database");

		updateResponse.setTransporterId(transporter.getTransporterId());
		updateResponse.setPhoneNo(transporter.getPhoneNo());
		updateResponse.setTransporterName(transporter.getTransporterName());
		updateResponse.setTransporterLocation(transporter.getTransporterLocation());
		updateResponse.setCompanyName(transporter.getCompanyName());
		updateResponse.setKyc(transporter.getKyc());
		updateResponse.setTransporterApproved(transporter.isTransporterApproved());
		updateResponse.setCompanyApproved(transporter.isCompanyApproved());
		updateResponse.setAccountVerificationInProgress(transporter.isAccountVerificationInProgress());
		updateResponse.setStatus(CommonConstants.success);
		updateResponse.setMessage(CommonConstants.updateSuccess);

		log.info("updateTransporter response is returned");
		return updateResponse;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteTransporter(String transporterId) {
		log.info("deleteTransporter service is started");
		Optional<Transporter> T = transporterdao.findById(transporterId);
		if(T.isEmpty())
			throw new EntityNotFoundException(Transporter.class, "id", transporterId.toString());

		transporterdao.deleteById(transporterId);
		log.info("transporter is deleted in the database");
	}

}
