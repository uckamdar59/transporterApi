package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.UUID;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.TransporterResponse;

public interface TransporterService {

	TransporterResponse addTransporter(Transporter transporter);
	
	List<Transporter> getApproved(boolean approved);

	TransporterResponse updateTransporter(UUID id, Transporter transporter);

	TransporterResponse deleteTransporter(UUID id);

	List<Transporter> allTransporter();

}
