package com.springboot.TransporterAPI.Dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.TransporterAPI.Entity.Transporter;

public interface TransporterDao extends JpaRepository<Transporter, String>  {
	@Query("select phoneNo from Transporter t where t.phoneNo = :phoneNo")
	String findByPhoneNo(long phoneNo);
	
	@Query("select t from Transporter t where t.approved = :approved")
	List<Transporter> findByApproved(Boolean approved);
	
}
