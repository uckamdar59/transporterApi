package com.springboot.TransporterAPI.Services;

import java.util.List;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

public interface TransporterService {

	TransporterCreateResponse addTransporter(PostTransporter postTransporter);
	
	List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, Integer pageNo);

	TransporterUpdateResponse updateTransporter(String transporterId, UpdateTransporter updatetransporter);

	TransporterDeleteResponse deleteTransporter(String transporterId);

	Transporter getOneTransporter(String transporterId);

}
