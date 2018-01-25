package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Address;
import com.revature.caliber.beans.Grade;
import com.revature.caliber.beans.TrainingStatus;

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
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Grade findByGradeId(long gradeId);
	
	
	
	/**
	 * Returns grades for all trainees that took a particular assignment. Great
	 * for finding average/median/highest/lowest grades for a test
	 * 
	 * @param assessmentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Grade> findByAssessmentAssessmentId(Long assessmentId) ;

}
