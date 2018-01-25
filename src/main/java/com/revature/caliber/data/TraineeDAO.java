package com.revature.caliber.data;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Trainee;

@Repository
public interface TraineeDAO extends JpaRepository<Trainee, Integer> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findAll();
	
	@Query("SELECT * FROM CALIBER_TRAINEE ct WHERE ct.TRAININGSTATUS != 'dropped'") 
	public List<Trainee> findAllNotDropped();
	
	public List<Trainee> findAllByBatchid(Integer BID);
	
}
