package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

public interface TransporterService {

	TransporterCreateResponse addTransporter(Transporter transporter);
	
	List<Transporter> getApproved(Boolean approved);

	TransporterUpdateResponse updateTransporter(String id, Transporter transporter);

	TransporterDeleteResponse deleteTransporter(String id);

	List<Transporter> allTransporter();

	Transporter getOneTransporter(String id);

}
