package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.LoadTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterDeleteResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

public interface TransporterService {

	TransporterCreateResponse addTransporter(LoadTransporter loadTransporter);
	
	List<Transporter> getTransporters(Boolean approved, Integer pageNo);

	TransporterUpdateResponse updateTransporter(String id, LoadTransporter updatetransporter);

	TransporterDeleteResponse deleteTransporter(String id);

	Transporter getOneTransporter(String id);

}
