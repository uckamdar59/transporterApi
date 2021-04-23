package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.TransporterCreateRequest;
import com.springboot.TransporterAPI.Model.TransporterDeleteRequest;
import com.springboot.TransporterAPI.Model.TransporterUpdateRequest;

public interface TransporterService {

	TransporterCreateRequest addTransporter(Transporter transporter);
	
	List<Transporter> getApproved(Boolean approved);

	TransporterUpdateRequest updateTransporter(UUID id, Transporter transporter);

	TransporterDeleteRequest deleteTransporter(UUID id);

	List<Transporter> allTransporter();

}
