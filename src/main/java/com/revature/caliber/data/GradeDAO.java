package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Address;
import com.revature.caliber.beans.Grade;
import com.revature.caliber.beans.TrainingStatus;

@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public interface GradeDAO extends JpaRepository<Grade, Integer>{

	static final Logger log = Logger.getLogger(GradeDAO.class);
	//SessionFactory sessionFactory;
	static final String TRAINEE = "trainee";
	static final String TRAINEE_TRAINING_STATUS = "trainee.trainingStatus";
	static final String TRAINEE_BATCH = "trainee.batch";
	
	
	
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//public List<Grade> findByAssessmentAssessmentId(Long assessmentId) ;
	
	/**
	 * Returns the grade given the id of the grade. Mainly used for testing.
	 * 
	 * @param gradeId
	 * @return
	 */
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Grade findByGradeId(long gradeId);
	
	
	
	/**
	 * Returns grades for all trainees that took a particular assignment. Great
	 * for finding average/median/highest/lowest grades for a test
	 * 
	 * @param assessmentId
	 * @return
	 */
	//@SuppressWarnings("unchecked")
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Grade> findByAssessmentAssessmentId(Long assessmentId) ;
	
	
	/**
	 * Returns all grades for a trainee. Useful for generating a full-view of
	 * individual trainee performance.
	 * 
	 * @param traineeId
	 * @return
	 */
	//@SuppressWarnings("unchecked")
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Grade> findByTraineeTraineeId(Integer traineeId);
	
	
	/**
	 * Returns all grades for a category. Useful for improving performance time
	 * of company-wide reporting
	 * 
	 * @param batchId
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@Query("SELECT g FROM Grade g left join fetch g.assessment a left join fetch a.category c where c.categoryId=?1")
	public List<Grade> findByCategoryId(Integer categoryId);

	
	/**
	 * Returns all grades for a batch. Useful for calculating coarsely-grained
	 * data for reporting.
	 * 
	 * @param batchId
	 * @return
	 */
	@Query("SELECT g FROM Grade g left join fetch g.trainee t left join fetch t.batch b where b.batchId=?1")
	public List<Grade> findByBatchId(Integer batchId);

}
