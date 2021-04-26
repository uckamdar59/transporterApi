package com.springboot.TransporterAPI.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.TransporterAPI.Entity.Transporter;

public interface TransporterDao extends JpaRepository<Transporter, UUID>  {
	@Query("select phoneNo from Transporter t where t.phoneNo = :phoneNo")
	String findByPhoneNo(long phoneNo);
	
	@Query("select t from Transporter t where t.approved = :approved")
	List<Transporter> findByApproved(Boolean approved);
	
	@Query("select t from Transporter t where t.id = :id")
	Optional<Transporter> findById(String id);
	
	@Query("select t from Transporter t where t.id = :id")
	Transporter getById(String id);
}
