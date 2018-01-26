package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Trainer;

@Repository
public interface TrainerDAO extends JpaRepository<Trainer, Long> {

	@Query("select distinct t from Trainer t where t.tier<>com.revature.caliber.beans.TrainerRole.ROLE_INACTIVE")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	List<Trainer> findAll();

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	Trainer findByTrainerId(int id);
	
	@Query("select distinct t.title from Trainer t")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	List<String> findAllTrainerTitles();
	
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	Trainer findByEmail(String email);
}
