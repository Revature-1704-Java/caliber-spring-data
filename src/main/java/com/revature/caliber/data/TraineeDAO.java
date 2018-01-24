package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.Salesforce;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.beans.TrainingStatus;

/**
 * @author Patrick Walsh
 * @author Ateeb Khawaja
 *
 */
@Repository
public interface TraineeDAO extends JpaRepository<Trainee, Integer>{

	static final Logger log = Logger.getLogger(TraineeDAO.class);
	//SessionFactory sessionFactory;
	static final String GRADES = "grades";
	static final String TRAINING_STATUS = "trainingStatus";
	static final String FETCH_TRAINEE = "Fetch trainee by email address: ";

	/**
	 * Find all trainees in a given batch
	 * 
	 * @param batchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findAllByBatch(Integer batchId);

	/**
	 * Find all trainees by the trainer's identifier
	 * 
	 * @param trainerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findByTrainer(Integer trainerId);

	/**
	 * * Find a trainee by the given identifier
	 * 
	 * @param traineeId
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Trainee findByTraineeId(Integer traineeId);

	/**
	 * Find a trainee by email address
	 * 
	 * @param email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findByEmail(String email);
	
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findByName(String name);
	
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Trainee> findBySkypeId(String skypeId);

}
