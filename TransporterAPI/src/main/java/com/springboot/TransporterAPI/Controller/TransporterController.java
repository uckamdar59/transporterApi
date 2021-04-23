package com.springboot.TransporterAPI.Controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.TransporterCreateRequest;
import com.springboot.TransporterAPI.Model.TransporterDeleteRequest;
import com.springboot.TransporterAPI.Model.TransporterUpdateRequest;
import com.springboot.TransporterAPI.Services.TransporterService;

@RestController
public class TransporterController {
	
	@Autowired
	private TransporterService service;
	
	@PostMapping("/transporter")
	public TransporterCreateRequest addTransporter(@RequestBody Transporter transporter) {
		return service.addTransporter(transporter);
	}
	
	
	@GetMapping("/transporter")
	public List<Transporter> getApproved(@RequestParam(required = false) Boolean approved){
		if(approved==null) {
			return service.allTransporter();
		}
		else {
			return service.getApproved(approved);
		}
	}
	
	
	@PutMapping("/transporter/{id}")
	public TransporterUpdateRequest updateTransporter(@PathVariable UUID id, @RequestBody Transporter transporter){
		return service.updateTransporter(id, transporter);
	}
	
	
	@DeleteMapping("/transporter/{id}")
	public TransporterDeleteRequest deleteTransporter(@PathVariable UUID id){
		return service.deleteTransporter(id);
	}

}
