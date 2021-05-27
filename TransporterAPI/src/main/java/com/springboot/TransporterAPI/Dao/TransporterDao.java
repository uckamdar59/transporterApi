package com.springboot.TransporterAPI.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.TransporterAPI.Entity.Transporter;

@Repository
public interface TransporterDao extends JpaRepository<Transporter, String>  {
	@Query("select name from Transporter t where t.phoneNo = :phoneNo")
	public String findByPhoneNo(Long phoneNo);
	
	public List<Transporter> findByApproved(Boolean approved, Pageable pageable);
	
}
