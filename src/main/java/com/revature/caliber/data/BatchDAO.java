package com.revature.caliber.data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.TrainingStatus;

@Repository
public interface BatchDAO extends JpaRepository<Batch, Integer>, BatchDAOCustom{
	static final Logger log = Logger.getLogger(BatchDAO.class);
	@Query("select distinct b from Batch b left join fetch b.trainees t where "
			+ "t.trainingStatus<>com.revature.caliber.beans.TrainingStatus.Dropped "
			+ "or t.trainingStatus is null order by b.startDate desc")
	public List<Batch> findAll();
	@Query("select distinct b from Batch b where b.trainer.trainerId=:id "
			+ "or b.coTrainer.trainerId=:id order by b.startDate desc")
	public List<Batch>findAllByTrainer(@Param("id") int trainerId);
//	//SessionFactory sessionFactory;
//	/**
//	 * Looks for all batches where the user was the trainer or co-trainer.
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllByTrainer(Integer trainerId);
//
//	/**
//	 * Looks for all batches where the user was the trainer or co-trainer. Batches
//	 * returned are currently actively in training.
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrent(Integer trainerId);
//
//	/**
//	 * Looks for all batches that are currently actively in training including
//	 * trainees, notes and grades
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrentWithNotesAndTrainees();
//
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrentWithNotes();
//
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findAllCurrentWithTrainees();
//
//	/**
//	 * Looks for all batches that are currently actively in training. Useful for VP
//	 * and QC to get snapshots of currently operating batches.
//	 * 
//	 * @param auth
//	 * @returnF
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)

//	public List<Batch> findAllCurrent();
//
//	/**
//	 * Find a batch by its given identifier
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public Batch findByBatchId(Integer batchId);
//
//	/**
//	 * Find a batch by its given identifier
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public Batch findOneWithDroppedTrainees(Integer batchId);
//
//	/**
//	 * Find a batch by its given identifier, all trainees, and all their grades
//	 * 
//	 * @param id
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public Batch findOneWithTraineesAndGrades(Integer batchId);
//
//	/**
//	 * Looks for all batches that whose starting date is after the given year,
//	 * month, and day. Month is 0-indexed Return all batches, trainees for that
//	 * batch, and the grades for each trainee
//	 * 
//	 * @param auth
//	 * @return
//	 */
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//	public List<Batch> findByDateLessThan(Integer month, Integer day, Integer year);
}
