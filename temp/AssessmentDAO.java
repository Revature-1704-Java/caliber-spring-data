package com.revature.caliber.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Address;
import com.revature.caliber.beans.Assessment;
import com.revature.caliber.beans.TrainingStatus;

/**
 * 
 * @author Patrick Walsh
 * @author Ateeb Khawaja
 * 
 *
 */
@Repository
public interface AssessmentDAO extends JpaRepository<Assessment, Integer>{

	static final Logger log = Logger.getLogger(AssessmentDAO.class);
	//SessionFactory sessionFactory;
	static final String BATCH = "batch";

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Assessment findById(long id);

	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Assessment> findByWeek(Integer batchId, Integer week);

	@SuppressWarnings("unchecked")
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Assessment> findByBatchId(Integer batchId);

	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	//public void delete(Assessment assessment);
}
