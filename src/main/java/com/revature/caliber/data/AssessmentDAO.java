package com.revature.caliber.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Assessment;

@Repository
public interface AssessmentDAO extends JpaRepository<Assessment, Long> {

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	Assessment findByAssessmentId(long id);

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	List<Assessment> findDistinctByWeek(short week);

	@Query("select distinct a from Assessment a left join fetch a.batch b left join fetch b.trainees t where b.batchId = ?1 and t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped or t.trainingStatus is null")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	List<Assessment> findDistinctByBatchBatchId(int id);

	@Query("select distinct a from Assessment a left join fetch a.batch b left join fetch b.trainees t where b.batchId = ?1 and a.week = ?2 and t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped or t.trainingStatus is null")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	List<Assessment> findByBatchIdAndWeek(int id, short week);
}
