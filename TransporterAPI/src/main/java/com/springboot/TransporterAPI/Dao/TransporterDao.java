package com.springboot.TransporterAPI.Dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.TransporterAPI.Entity.Transporter;

@Repository
public interface TransporterDao extends JpaRepository<Transporter, String>  {
	@Query("select id from Transporter t where t.phoneNo = :phoneNo")
	public String findByPhoneNo(Long phoneNo);
	
	@Query("select t from Transporter t where t.transporterApproved = :approved AND t.companyApproved = :approved")
	public List<Transporter> findByApprovedSuccess(Boolean approved, Pageable pageable);
	
	@Query("select t from Transporter t where t.transporterApproved = :approved OR t.companyApproved = :approved")
	public List<Transporter> findByApprovedPending(Boolean approved, Pageable pageable);
}
