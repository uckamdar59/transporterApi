package com.springboot.TransporterAPI.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Services.TransporterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
public class TransporterController {

	@Autowired
	private TransporterService service;
	@Autowired
	private com.springboot.TransporterAPI.SecurityConfig.JwtTokenUtil jwtTokenUtil;

	@GetMapping("/home")
	public String home() {
		return "Welcome to transporterApi git action check 2...!!!";
	}

	@PostMapping("/transporter")
	public ResponseEntity<Object> addTransporter(@Valid @RequestBody  PostTransporter transporter) {
		log.info("Post Controller Started");
		final String token = jwtTokenUtil.generateToken(transporter);
		return new ResponseEntity<>(service.addTransporter(transporter),HttpStatus.CREATED);
	}


	@GetMapping("/transporters")
	public ResponseEntity<List<Transporter>> getTransporters(
			@RequestParam(required = false) Boolean transporterApproved,
			@RequestParam(required = false) Boolean companyApproved,
			@RequestParam(required = false) Integer pageNo){
		log.info("Get with Params Controller Started");
		return new ResponseEntity<>(service.getTransporters(transporterApproved, companyApproved, pageNo),HttpStatus.OK);
	}

	@GetMapping("/transporter/{transporterId}")
	private ResponseEntity<Object> getOneTransporter(@PathVariable String transporterId) {
		log.info("Get by transporterId Controller Started");
		return new ResponseEntity<>( service.getOneTransporter(transporterId),HttpStatus.OK);
	}


	@PutMapping("/transporter/{transporterId}")
	public ResponseEntity<Object> updateTransporter(@PathVariable String transporterId, @RequestBody UpdateTransporter transporter){
		log.info("Put Controller Started");
		return new ResponseEntity<>(service.updateTransporter(transporterId, transporter),HttpStatus.OK);
	}


	@DeleteMapping("/transporter/{transporterId}")
	public ResponseEntity<Object> deleteTransporter(@PathVariable String transporterId){
		log.info("Delete Controller Started");
		service.deleteTransporter(transporterId);
		return new ResponseEntity<>("Sucessfully deleted",HttpStatus.OK);
	}

}
