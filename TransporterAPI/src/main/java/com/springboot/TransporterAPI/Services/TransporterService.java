package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

public interface TransporterService {

	TransporterCreateResponse addTransporter(PostTransporter postTransporter);
	
	List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, Integer pageNo);

	TransporterUpdateResponse updateTransporter(String id, UpdateTransporter updatetransporter);

	TransporterDeleteResponse deleteTransporter(String id);

	Transporter getOneTransporter(String id);

}
